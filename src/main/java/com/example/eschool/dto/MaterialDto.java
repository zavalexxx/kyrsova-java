package com.example.eschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {
    private Long Id;
    private String name;
    private String description;
    private String fileURL;
}
