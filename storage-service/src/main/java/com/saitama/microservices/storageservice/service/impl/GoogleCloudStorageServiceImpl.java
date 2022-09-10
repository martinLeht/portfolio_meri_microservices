package com.saitama.microservices.storageservice.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.saitama.microservices.commonlib.exception.CommonInternalException;
import com.saitama.microservices.storageservice.constant.MediaCategory;
import com.saitama.microservices.storageservice.service.ICloudStorageService;


@Service
@PropertySource("classpath:/config/gcs.properties")
public class GoogleCloudStorageServiceImpl implements ICloudStorageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCloudStorageServiceImpl.class);
	private static final String STORAGE_API = "https://storage.googleapis.com";
	
	@Autowired
    private Storage storage;

    @Value("${bucket.bucketname}")
    private String bucketName;

	@Override
	public URL uploadFile(MediaCategory mediaCategory, MultipartFile file) {
		try {
			final byte[] byteArray = file.getBytes();
			if (mediaCategory != null) {
				return saveAndCreateUrl(byteArray, mediaCategory.categoryName, file.getOriginalFilename());
			} else {
				return saveAndCreateUrl(byteArray, MediaCategory.GENERAL.categoryName, file.getOriginalFilename());
			}
		} catch (MalformedURLException ex) {
			throw new CommonInternalException("failed-to-upload-file", "Something went wrong in resource URL formation");
		} catch (IOException e) {
			throw new CommonInternalException("failed-to-upload-file", "Something went wrong in file serialization");
		}
		
	}
	
	private URL saveAndCreateUrl(byte[] bytes, String subdirectory, String fileName) throws MalformedURLException {
		// Prepare the blobId
		// BlobId is a combination of bucketName + subdirectiory(optional) + fileName
		final BlobId blobId = constructBlobId(bucketName, subdirectory, fileName);
		BlobInfo info = BlobInfo.newBuilder(blobId).build();
		Blob blob = storage.create(info, bytes);
		return createStorageResourceUrl(subdirectory, fileName);
	}
	
	private URL createStorageResourceUrl(String subdirectory, String fileName) throws MalformedURLException {
		StringBuilder urlSb = new StringBuilder(STORAGE_API);
		urlSb.append("/").append(bucketName).append("/").append(subdirectory).append("/").append(fileName);
		return new URL(urlSb.toString());
	}
	
	@Override
	public Optional<URL> getFileByName(MediaCategory mediaCategory, String name) {
		final BlobId blobId = constructBlobId(bucketName, mediaCategory.categoryName, name);
		Blob blob = storage.get(blobId);
		if (blob != null) {
			BlobInfo info = BlobInfo.newBuilder(blobId).build();
			URL url;
			try {
				url = createStorageResourceUrl(mediaCategory.categoryName, name);
			} catch (MalformedURLException e) {
				throw new CommonInternalException("failed-to-upload-file", "Something went wrong in resource URL formation");
			}
			return Optional.of(url);
		}
		return Optional.empty();
	}
	
	@Override
	public Map<String, URL> getFilesByNames(MediaCategory mediaCategory, List<String> names) {
		List<BlobId> blobIds = names.stream()
				.map(name -> constructBlobId(bucketName, mediaCategory.categoryName, name))
				.collect(Collectors.toList());
		List<Blob> blobs = storage.get(blobIds);
		if (blobs.size() > 0) {
			Map<String, URL> fileUrls = new HashMap<>();
			for (int i = 0 ; i < blobIds.size() ; i++) {
				BlobInfo info = BlobInfo.newBuilder(blobIds.get(i)).build();
				//URL url = createSignedPathStyleUrl(info, 7, TimeUnit.DAYS);
				URL url;
				try {
					url = createStorageResourceUrl(mediaCategory.categoryName, info.getName());
					fileUrls.put(names.get(i), url);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return fileUrls;
		}
		return Collections.emptyMap();
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
				.orElse(BlobId.of(bucketName, MediaCategory.GENERAL.categoryName + "/" + fileName));
	}

	@Override
	public boolean deleteFile(MediaCategory mediaCategory, String fileName) {
		final BlobId blobId = constructBlobId(bucketName, mediaCategory.categoryName, fileName);
		return storage.delete(blobId);
	}
	
	@Override
	public List<Boolean> deleteFiles(MediaCategory mediaCategory, List<String> fileNames) {
		BlobId[] blobIds = new BlobId[fileNames.size()];
		for (int i = 0; i < fileNames.size(); i++) {
			blobIds[i] = constructBlobId(bucketName, mediaCategory.categoryName, fileNames.get(i));
		}
		return storage.delete(blobIds);
	}

}
