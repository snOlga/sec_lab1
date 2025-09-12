package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PlantDTO;
import com.example.demo.entity.PlantEntity;
import com.example.demo.repository.PlantRepository;

@Service
public class PlantConverter {

    @Autowired
    private PlantRepository plantRepository;

    public PlantEntity fromDTO(PlantDTO dto) {
        if (dto == null || dto.getName() == null || dto.getLeafLength() == null)
            return null;
            
        PlantEntity plantEntity = new PlantEntity();
        plantEntity.setId(dto.getId());
        plantEntity.setName(dto.getName());
        plantEntity.setLeafLength(dto.getLeafLength());

        return plantEntity;
    }

    public PlantDTO fromEntity(PlantEntity entity) {
        if (entity == null || entity.getName() == null || entity.getLeafLength() == null)
            return null;

        return new PlantDTO(entity.getId(), entity.getName(), entity.getLeafLength());
    }

    public PlantEntity fromId(Long id) {
        return plantRepository.findById(id).orElse(null);
    }
}
