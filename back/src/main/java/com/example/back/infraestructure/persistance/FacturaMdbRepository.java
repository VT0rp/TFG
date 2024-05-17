package com.example.back.infraestructure.persistance;

import com.example.back.domain.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FacturaMdbRepository extends MongoRepository<Factura, String> {

    Page<Factura> findAllByEmailAndNombreStartingWithIgnoreCase(String email,String nombre, Pageable pageable);

    Page<Factura> findAllByUserIdAndAndNombreStartingWithIgnoreCase(String userId, String nombre, Pageable pageable);
    Page<Factura> findAllByEmail(String email, Pageable pageable);

    Page<Factura> findAllByUserId(String userId, Pageable pageable);

    Optional<Factura> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);

    Boolean existsByNombreAndEmail(String nombre, String email);

    Optional<Factura> findByNombreAndEmail(String nombre, String email);
}
