package com.example.eschool.controllers;


import com.example.eschool.dto.MaterialDto;
import com.example.eschool.services.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/materials")
@Tag(name = "Керування матеріалами до уроків")
public class MaterialController {
    private final MaterialService materialService;

    @Operation(
            description = "Додати новий матеріал",
            summary = "Додає в систему новий матеріал"
    )
    @PostMapping("/add")
    public ResponseEntity<MaterialDto> createMaterial(@RequestBody MaterialDto materialDto) {
        MaterialDto createdMaterial = materialService.createMaterial(materialDto);
        return new ResponseEntity<>(createdMaterial, HttpStatus.CREATED);
    }

    @Operation(
            description = "Переглянути інформацію про матеріал",
            summary = "Повертає інформацію про конкретний матеріал"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<MaterialDto> getMaterialById(@PathVariable Long id) {
        MaterialDto materialDto = materialService.getMaterialById(id);
        return new ResponseEntity<>(materialDto, HttpStatus.OK);
    }

    @Operation(
            description = "Переглянути інформацію про всі матеріали",
            summary = "Повертає список всіх матеріалів в системі"
    )
    @GetMapping("/getAll")
    public ResponseEntity<List<MaterialDto>> getAllMaterials() {
        List<MaterialDto> materials = materialService.getAllMaterials();
        return new ResponseEntity<>(materials, HttpStatus.OK);
    }

    @Operation(
            description = "Оновити інформацію про матеріал",
            summary = "Оновлення інформації про матеріал"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<MaterialDto> updateMaterial(@PathVariable Long id, @RequestBody MaterialDto materialDto) {
        MaterialDto updatedMaterial = materialService.updateMaterial(id, materialDto);
        return new ResponseEntity<>(updatedMaterial, HttpStatus.OK);
    }

    @Operation(
            description = "Видалити матеріал",
            summary = "Видаляє матеріал з системи"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
