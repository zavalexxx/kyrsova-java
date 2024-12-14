package com.example.eschool.services.implementation;

import com.example.eschool.dto.MaterialDto;
import com.example.eschool.entities.Material;
import com.example.eschool.mapper.MaterialMapper;
import com.example.eschool.repositories.MaterialRepository;
import com.example.eschool.services.MaterialService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService {
    
    private MaterialRepository materialRepository;

    /**
     * Create a new material.
     *
     * @param materialDto The material DTO containing information about the material to be created.
     * @return The DTO of the created material.
     */
    @Override
    public MaterialDto createMaterial(MaterialDto materialDto) {
        try {
            Material material = MaterialMapper.mapToMaterial(materialDto);
            Material savedMaterial = materialRepository.save(material);
            return MaterialMapper.mapToMaterialDto(savedMaterial);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create material", e);
        }
    }

    /**
     * Get a material by its ID.
     *
     * @param id The ID of the material to retrieve.
     * @return The DTO of the retrieved material.
     * @throws EntityNotFoundException if the material with the specified ID is not found.
     */
    @Override
    public MaterialDto getMaterialById(Long id) {
        try {
            Material material = materialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Material not found with ID: " + id));
            return MaterialMapper.mapToMaterialDto(material);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get material by ID", e);
        }
    }

    /**
     * Get all materials.
     *
     * @return A list of DTOs representing all materials.
     */
    @Override
    public List<MaterialDto> getAllMaterials() {
        try {
            return materialRepository.findAll().stream()
                    .map(MaterialMapper::mapToMaterialDto).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all materials", e);
        }
    }

    /**
     * Update a material.
     *
     * @param id          The ID of the material to update.
     * @param materialDto The DTO containing the updated information.
     * @return The DTO of the updated material.
     * @throws EntityNotFoundException if the material with the specified ID is not found.
     */
    @Override
    public MaterialDto updateMaterial(Long id, MaterialDto materialDto) {
        try {
            Material existingMaterial = materialRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Material not found with ID: " + id));

            existingMaterial.setName(materialDto.getName());
            existingMaterial.setFileURL(materialDto.getFileURL());

            Material updatedMaterial = materialRepository.save(existingMaterial);
            return MaterialMapper.mapToMaterialDto(updatedMaterial);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update material", e);
        }
    }

    /**
     * Delete a material by its ID.
     *
     * @param id The ID of the material to delete.
     * @throws EntityNotFoundException if the material with the specified ID is not found.
     */
    @Override
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new EntityNotFoundException("Material not found with ID: " + id);
        }
        materialRepository.deleteById(id);
    }
}
