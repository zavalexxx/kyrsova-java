package com.example.eschool.mapper;

import com.example.eschool.dto.MaterialDto;
import com.example.eschool.entities.Material;

public class MaterialMapper {
    public static MaterialDto mapToMaterialDto(Material material) {
        return new MaterialDto(
                material.getId(),
                material.getName(),
                material.getDescription(),
                material.getFileURL()
        );
    }
    public static Material mapToMaterial(MaterialDto materialDto) {
        return new Material (
                materialDto.getId(),
                materialDto.getName(),
                materialDto.getDescription(),
                materialDto.getFileURL()
        );
    }
}
