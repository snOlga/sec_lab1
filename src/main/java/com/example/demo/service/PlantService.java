package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.converter.PlantConverter;
import com.example.demo.dto.PlantDTO;
import com.example.demo.entity.PlantEntity;
import com.example.demo.repository.PlantRepository;

@Service
public class PlantService {

    @Autowired
    private PlantRepository repository;
    @Autowired
    private PlantConverter converter;

    public PlantDTO create(PlantDTO dto) {
        dto.setId(null);
        return converter.fromEntity(repository.save(converter.fromDTO(dto)));
    }

    public PlantDTO read(PlantDTO dto) {
        if (dto.getId() == null)
            return null;
        return converter.fromEntity(repository.findById(dto.getId()).orElse(null));
    }

    public PlantDTO update(PlantDTO dto) {
        return converter.fromEntity(repository.save(converter.fromDTO(dto)));
    }

    public PlantDTO delete(PlantDTO dto) {
        if (dto.getId() == null)
            return null;
        PlantEntity entity = repository.findById(dto.getId()).orElse(null);
        if (entity == null) 
            return null;
        repository.delete(entity);
        return converter.fromEntity(entity);
    }
}
