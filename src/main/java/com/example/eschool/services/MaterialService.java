package com.example.eschool.services;

import com.example.eschool.dto.MaterialDto;

import java.util.List;

public interface MaterialService {
    MaterialDto createMaterial(MaterialDto materialDto);
    MaterialDto getMaterialById(Long id);
    List<MaterialDto> getAllMaterials();
    MaterialDto updateMaterial(Long id, MaterialDto materialDto);
    void deleteMaterial(Long id);
}
