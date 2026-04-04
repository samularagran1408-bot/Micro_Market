package com.Micro_Marlet.Inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Micro_Marlet.Inventario.DTO.Sale_DetailsRequestDTO;
import com.Micro_Marlet.Inventario.DTO.Sale_DetailsResponseDTO;
import com.Micro_Marlet.Inventario.entity.Sale_Details;
import com.Micro_Marlet.Inventario.repository.Sale_DetailsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Sale_DetailsService {

    private final Sale_DetailsRepository repository;

    @Transactional(readOnly = true)
    public List<Sale_DetailsResponseDTO> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<Sale_DetailsResponseDTO> findById(Long id) {
        return repository.findById(id).map(this::toResponse);
    }

    @Transactional
    public Sale_DetailsResponseDTO create(Sale_DetailsRequestDTO request) {
        Sale_Details entity = new Sale_Details();
        applyRequest(entity, request);
        return toResponse(repository.save(entity));
    }

    @Transactional
    public Optional<Sale_DetailsResponseDTO> update(Long id, Sale_DetailsRequestDTO request) {
        return repository.findById(id).map(entity -> {
            applyRequest(entity, request);
            return toResponse(repository.save(entity));
        });
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    private void applyRequest(Sale_Details entity, Sale_DetailsRequestDTO body) {
        entity.setSaleId(body.getSaleId());
        entity.setProductId(body.getProductId());
        entity.setQuantity(body.getQuantity());
        entity.setUnitPrice(body.getUnitPrice());
        entity.setSubtotal(body.getQuantity() * body.getUnitPrice());
    }

    private Sale_DetailsResponseDTO toResponse(Sale_Details e) {
        return new Sale_DetailsResponseDTO(
                e.getId(),
                e.getSaleId(),
                e.getProductId(),
                e.getQuantity(),
                e.getUnitPrice(),
                e.getSubtotal());
    }
}
