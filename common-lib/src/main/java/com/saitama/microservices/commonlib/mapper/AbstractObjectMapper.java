package com.saitama.microservices.commonlib.mapper;

import java.util.function.Supplier;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractObjectMapper<Model, Dto> {

    protected final ObjectMapper mapper;
    protected final ModelMapper modelMapper;
    private final Class<Model> genericModelClass;
    private final Class<Dto> genericDtoClass;

    public AbstractObjectMapper(
            ObjectMapper mapper,
            ModelMapper modelMapper,
            Supplier<Model> modelSupplier,
            Supplier<Dto> dtoSupplier
    ) {
        this.mapper = mapper;
        this.modelMapper = modelMapper;
        this.genericModelClass = (Class<Model>) modelSupplier.get().getClass();
        this.genericDtoClass = (Class<Dto>) dtoSupplier.get().getClass();
    }

    /**
     * Convert the given DTO type of {@code <Dto>} to a Model object type of {@code <Model>}.
     *
     * @param dto The DTO object to be converted to a model
     * @return <Model> A model object type
     */
    public Model fromDto(Dto dto) {
        return mapper.convertValue(dto, genericModelClass);
    }

    /**
     * Convert the Model object type of {@code <Model>} to a DTO object type of {@code <Dto>}.
     *
     * @param model The model object to be converted to Dto
     * @return A model object
     */
    public Dto toDto(Model model) {
        return mapper.convertValue(model, genericDtoClass);
    }

    public Model updateFromDtoToModel(Model model, Dto dto) throws JsonProcessingException {
        return mapper.readerForUpdating(model).readValue(mapper.writeValueAsString(dto));
    }
    
    public void mapFromDtoToModel(Model model, Dto dto) {
        modelMapper.map(dto, model);
    }

}
