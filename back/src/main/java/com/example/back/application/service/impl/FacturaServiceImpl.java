package com.example.back.application.service.impl;

import com.example.back.application.dto.FacturaDto;
import com.example.back.application.mapper.FacturaMapper;
import com.example.back.application.service.FacturaService;
import com.example.back.domain.entity.Factura;
import com.example.back.domain.persistance.FacturaPersistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaMapper facturaMapper;

    private final FacturaPersistance facturaPersistance;

    public FacturaServiceImpl(FacturaMapper facturaMapper, FacturaPersistance facturaPersistance)
    {
        this.facturaMapper = facturaMapper;
        this.facturaPersistance = facturaPersistance;
    }

    @Override
    public Page<Factura> getPageByUserId(String userId, String nombre, Pageable pageable) {
        return facturaPersistance.findByUserId(userId, nombre, pageable);
    }
    @Override
    public Page<Factura> getPageByUserIdNot(String userId, Pageable pageable) {
        return facturaPersistance.findByUserIdNot(userId, pageable);
    }

    @Override
    public Page<Factura> getPageByEmail(String email, String nombre, Pageable pageable) {
        return facturaPersistance.find(email, nombre, pageable);
    }
    @Override
    public Page<Factura> getPageByEmailNot(String email, Pageable pageable) {
        return facturaPersistance.findNot(email, pageable);
    }

    @Override
    public Optional<FacturaDto> getById(String id) {
        Optional<Factura> factura = facturaPersistance.findById(id);
        if(factura.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(facturaMapper.toDto(factura.get()));
    }

    @Override
    public ResponseEntity update(String id, FacturaDto facturaDto) {
        return facturaPersistance.update(id, facturaDto);
    }

    @Override
    public Optional<FacturaDto> create(FacturaDto factura) {
        Optional<Factura> facturaCreada = facturaPersistance.create(facturaMapper.toEntity(factura));
        if(facturaCreada.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(facturaMapper.toDto(facturaCreada.get()));
    }

    @Override
    public ResponseEntity delete(String id) {
        return facturaPersistance.delete(id);
    }
}
