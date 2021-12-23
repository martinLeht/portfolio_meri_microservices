package com.saitama.microservices.imageservice.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;


@Service
@PropertySource("classpath:/config/gcs.properties")
public class GoogleCloudStorageService implements IGoogleCloudStorageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCloudStorageService.class);
	
	@Autowired
    private Storage storage;

    @Value("${bucketname}")
    String bucketName;
    
    @Value("${subdirectory}")
    String subdirectory;

	@Override
	public URL uploadFile(MultipartFile file) throws IOException {
		// Convert the file to a byte array
		final byte[] byteArray = file.getBytes();

		// Prepare the blobId
		// BlobId is a combination of bucketName + subdirectiory(optional) + fileName
		final BlobId blobId = constructBlobId(bucketName, subdirectory, file.getOriginalFilename());
		BlobInfo info = BlobInfo.newBuilder(blobId).build();
		Blob blob = storage.create(info, byteArray);
		URL url = createSignedPathStyleUrl(info, 7, TimeUnit.DAYS);
		return url;
	}

	private URL createSignedPathStyleUrl(BlobInfo blobInfo, int duration, TimeUnit timeUnit) {
		return storage.signUrl(blobInfo, duration, timeUnit, Storage.SignUrlOption.withPathStyle());
	}
	
	@Override
	public Optional<URL> getFileByName(String name) throws IOException {
		final BlobId blobId = constructBlobId(bucketName, subdirectory, name);
		Blob blob = storage.get(blobId);
		if (blob != null) {
			BlobInfo info = BlobInfo.newBuilder(blobId).build();
			URL url = createSignedPathStyleUrl(info, 7, TimeUnit.DAYS);
			return Optional.of(url);
		}
		return Optional.empty();
	}

	/**
	 * Construct Blob ID
	 *
	 * @param bucketName
	 * @param subdirectory optional
	 * @param fileName
	 * @return
	 */
	private BlobId constructBlobId(String bucketName, @Nullable String subdirectory, String fileName) {
		return Optional.ofNullable(subdirectory).map(s -> BlobId.of(bucketName, subdirectory + "/" + fileName))
				.orElse(BlobId.of(bucketName, fileName));
	}

	@Override
	public boolean deleteFile(String fileName) {
		final BlobId blobId = constructBlobId(bucketName, subdirectory, fileName);
		return storage.delete(blobId);
	}
	
	@Override
	public List<Boolean> deleteFiles(List<String> fileNames) {
		BlobId[] blobIds = new BlobId[fileNames.size()];
		for (int i = 0; i < fileNames.size(); i++) {
			blobIds[i] = constructBlobId(bucketName, subdirectory, fileNames.get(i));
		}
		return storage.delete(blobIds);
	}

}
