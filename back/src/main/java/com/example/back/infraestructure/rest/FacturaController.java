package com.example.back.infraestructure.rest;

import com.example.back.application.dto.FacturaDto;
import com.example.back.application.service.FacturaService;
import com.example.back.domain.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/factura")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService){
        this.facturaService = facturaService;
    }

    @GetMapping("/pagedUserId/{id}")
    public Page<Factura> getPageByUserId(@PathVariable("id") String userId, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("nombre")String nombre){
        Pageable pageable = PageRequest.of(page, size);
        return facturaService.getPageByUserId(userId, nombre, pageable);
    }

    @GetMapping("/pagedEmail")
    public Page<Factura> getPageByEmail(@RequestParam("email") String email, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("nombre") String nombre){
        Pageable pageable = PageRequest.of(page, size);
        return facturaService.getPageByEmail(email, nombre, pageable);
    }

    @GetMapping("/get/{id}")
    public Optional<FacturaDto> getById(@PathVariable("id") String id){
        return facturaService.getById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody FacturaDto facturaDto){
        return facturaService.update(id, facturaDto);
    }

    @PostMapping("/create")
    public Optional<FacturaDto> create(@RequestBody FacturaDto factura){
        return facturaService.create(factura);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        return facturaService.delete(id);
    }
}
