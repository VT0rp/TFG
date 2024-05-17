package com.example.back.application.service;

import com.example.back.application.dto.FacturaDto;
import com.example.back.domain.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface FacturaService {

    Page<Factura> getPageByUserId(String userId, String nombre, Pageable pageable);
    Page<Factura> getPageByEmail(String email, String nombre, Pageable pageable);

    Optional<FacturaDto> getById(String id);

    ResponseEntity update(String id, FacturaDto facturaDto);

    Optional<FacturaDto> create(FacturaDto factura);

    ResponseEntity delete(String id);
}
