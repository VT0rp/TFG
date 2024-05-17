package com.example.back.infraestructure.rest;

import com.example.back.application.dto.FacturaDto;
import com.example.back.application.service.FacturaService;
import com.example.back.domain.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/pagedUserIdNot/{id}")
    public Page<Factura> getPageByUserIdNot(@PathVariable("id") String userId, @RequestParam("page") int page, @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size);
        return facturaService.getPageByUserIdNot(userId, pageable);
    }

    @GetMapping("/pagedEmail")
    public Page<Factura> getPageByEmail(@RequestParam("email") String email, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("nombre") String nombre){
        Pageable pageable = PageRequest.of(page, size);
        return facturaService.getPageByEmail(email, nombre, pageable);
    }
    @GetMapping("/pagedEmailNot")
    public Page<Factura> getPageByEmailNot(@RequestParam("email") String email, @RequestParam("page") int page, @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(page, size);
        return facturaService.getPageByEmailNot(email, pageable);
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
    public ResponseEntity create(@RequestBody FacturaDto factura)
    {
        Optional<FacturaDto> isThereFactura = facturaService.create(factura);
        if(isThereFactura.isEmpty()){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        return facturaService.delete(id);
    }
}
