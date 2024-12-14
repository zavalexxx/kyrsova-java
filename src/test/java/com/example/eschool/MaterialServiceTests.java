package com.example.eschool;
import com.example.eschool.dto.MaterialDto;
import com.example.eschool.entities.Material;
import com.example.eschool.mapper.MaterialMapper;
import com.example.eschool.repositories.MaterialRepository;
import com.example.eschool.services.implementation.MaterialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MaterialServiceTests {
    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialServiceImpl materialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMaterial_Success() {
        MaterialDto materialDto = new MaterialDto();
        materialDto.setName("Test Material");
        materialDto.setFileURL("testfile.pdf");

        Material material = MaterialMapper.mapToMaterial(materialDto);
        when(materialRepository.save(any(Material.class))).thenReturn(material);

        MaterialDto savedMaterialDto = materialService.createMaterial(materialDto);

        assertNotNull(savedMaterialDto);
        assertEquals(materialDto.getName(), savedMaterialDto.getName());
        assertEquals(materialDto.getFileURL(), savedMaterialDto.getFileURL());
    }

    @Test
    void getMaterialById_ExistingId_Success() {
        Long materialId = 1L;
        Material material = new Material();
        material.setId(materialId);
        material.setName("Test Material");
        material.setFileURL("testfile.pdf");

        when(materialRepository.findById(materialId)).thenReturn(Optional.of(material));

        MaterialDto materialDto = materialService.getMaterialById(materialId);

        assertNotNull(materialDto);
        assertEquals(material.getId(), materialDto.getId());
        assertEquals(material.getName(), materialDto.getName());
        assertEquals(material.getFileURL(), materialDto.getFileURL());
    }

    @Test
    void getMaterialById_NonExistingId_EntityNotFoundExceptionThrown() {
        Long materialId = 1L;
        when(materialRepository.findById(materialId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> materialService.getMaterialById(materialId));
    }

    @Test
    void getAllMaterials_Success() {
        List<Material> materials = new ArrayList<>();
        materials.add(new Material());
        materials.add(new Material());
        materials.add(new Material());

        when(materialRepository.findAll()).thenReturn(materials);

        List<MaterialDto> materialDtos = materialService.getAllMaterials();

        assertNotNull(materialDtos);
        assertEquals(materials.size(), materialDtos.size());
    }

    // Add more test cases for other methods as needed
}
