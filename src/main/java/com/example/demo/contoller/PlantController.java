package com.example.demo.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlantDTO;
import com.example.demo.service.PlantService;

@RestController
@RequestMapping("/plants")
public class PlantController {

    @Autowired
    PlantService service;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<PlantDTO> createPlant(@RequestBody PlantDTO dto) {
        PlantDTO result = service.create(dto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<PlantDTO>> readPlant() {
        List<PlantDTO> result = service.readAll();
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public ResponseEntity<PlantDTO> updatePlant(@RequestBody PlantDTO dto) {
        PlantDTO result = service.update(dto);
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping
    public ResponseEntity<PlantDTO> deletePlant(@RequestParam Long id) {
        PlantDTO result = service.delete(new PlantDTO(id, null, null));
        return new ResponseEntity<>(result, result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
