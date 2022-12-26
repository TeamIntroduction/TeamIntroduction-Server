package com.example.project.dto.part;

import com.example.project.domain.part.Part;
import com.example.project.domain.part.PartName;
import lombok.Data;

@Data
public class PartResDto {
    private Long id;
    private PartName name;


    public PartResDto(Part part) {
        this.id = part.getId();
        this.name = part.getName();
    }
}
