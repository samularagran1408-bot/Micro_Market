package com.Micro_Marlet.Inventario.service;

import com.Micro_Marlet.Inventario.DTO.CategoriesRequestDTO;
import com.Micro_Marlet.Inventario.DTO.CategoriesResponseDTO;
import com.Micro_Marlet.Inventario.entity.Categories;
import com.Micro_Marlet.Inventario.repository.CategoriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<CategoriesResponseDTO> getAllActive() {
        return categoriesRepository.findAllByStatusTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoriesResponseDTO getById(Long id) {
        Categories entity = categoriesRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category no encontrada"));
        return toResponse(entity);
    }

    @Transactional
    public CategoriesResponseDTO create(CategoriesRequestDTO request) {
        Categories entity = new Categories();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(true);
        return toResponse(categoriesRepository.save(entity));
    }

    @Transactional
    public CategoriesResponseDTO update(Long id, CategoriesRequestDTO request) {
        Categories entity = categoriesRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category no encontrada"));
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return toResponse(categoriesRepository.save(entity));
    }

    @Transactional
    public void softDelete(Long id) {
        Categories entity = categoriesRepository.findByIdAndStatusTrue(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category no encontrada"));
        entity.setStatus(false);
        categoriesRepository.save(entity);
    }

    private CategoriesResponseDTO toResponse(Categories entity) {
        CategoriesResponseDTO dto = new CategoriesResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
