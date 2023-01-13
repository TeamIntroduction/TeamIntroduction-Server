package com.example.project.dto.key;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SymmetricKeyReqDto {

    @JsonProperty("sK")
    private String sK;
}
