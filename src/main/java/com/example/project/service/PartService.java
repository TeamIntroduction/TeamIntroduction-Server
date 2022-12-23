package com.example.project.service;

import com.example.project.dto.part.PartResDto;
import com.example.project.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    public List<PartResDto> getParts() {

        return partRepository.findAll().stream()
                .map(d -> new PartResDto(d))
                .collect(Collectors.toList());
    }
}
