package com.example.back.domain.persistance;

import com.example.back.application.dto.FacturaDto;
import com.example.back.domain.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface FacturaPersistance {

    Page<Factura> find(String email, String nombre, Pageable pageable);
    Page<Factura> findNot(String email, Pageable pageable);

    Optional<Factura> findById(String id);

    Page<Factura> findByUserId(String userId, String nombre, Pageable pageable);
    Page<Factura> findByUserIdNot(String userId, Pageable pageable);

    ResponseEntity update(String id, FacturaDto facturaDto);

    Optional<Factura> create(Factura factura);

    ResponseEntity delete(String id);
}
