package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PlantDTO {

    private Long id;

    private String name;

    private Integer leafLength;
}
