package com.example.back.infraestructure.persistance;

import com.example.back.application.dto.FacturaDto;
import com.example.back.domain.entity.Factura;
import com.example.back.domain.entity.Item;
import com.example.back.domain.entity.Role;
import com.example.back.domain.entity.User;
import com.example.back.domain.persistance.FacturaPersistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public class FacturaPersistanceImpl implements FacturaPersistance {

    private final FacturaMdbRepository facturaMdbRepository;
    private final UserMdbRepository userMdbRepository;

    public FacturaPersistanceImpl(FacturaMdbRepository facturaMdbRepository, UserMdbRepository userMdbRepository){
        this.facturaMdbRepository = facturaMdbRepository;
        this.userMdbRepository = userMdbRepository;
    }
    @Override
    public Page<Factura> find(String email, String nombre, Pageable pageable) {
        return facturaMdbRepository.findAllByEmailAndNombreStartingWithIgnoreCase(email, nombre, pageable);
    }
    @Override
    public Page<Factura> findNot(String email, Pageable pageable) {
        return facturaMdbRepository.findAllByEmail(email, pageable);
    }

    @Override
    public Optional<Factura> findById(String id) {
        return facturaMdbRepository.findById(id);
    }

    @Override
    public Page<Factura> findByUserId(String userId, String nombre, Pageable pageable) {
        return facturaMdbRepository.findAllByUserIdAndAndNombreStartingWithIgnoreCase(userId, nombre, pageable);
    }

    @Override
    public Page<Factura> findByUserIdNot(String userId, Pageable pageable) {
        return facturaMdbRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public ResponseEntity update(String id, FacturaDto facturaDto) {
        Optional<Factura> factura = facturaMdbRepository.findById(id);
        Optional<Factura> nombreExists = facturaMdbRepository.findByNombreAndEmail(facturaDto.getNombre(), facturaDto.getEmail());
        if(factura.isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if(nombreExists.isEmpty()){
            //En caso de que el usuario no exista lo podemos actualizar
            setValues(factura, facturaDto);
            return new ResponseEntity(HttpStatus.OK);
        }
        if(!nombreExists.get().getId().equals(id)){
            // Si existe el nombre y la id no es la misma no nos debe dejar
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        factura.get().setNombre(facturaDto.getNombre());
        factura.get().setDescuento(facturaDto.getDescuento());
        factura.get().setItems(facturaDto.getItems());
        setValues(factura, facturaDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public Optional<Factura> create(Factura factura){
        Optional<Factura> facturaEcontrada = facturaMdbRepository.findByNombreAndEmail(factura.getNombre(), factura.getEmail());
        if(facturaEcontrada.isPresent()){
            return Optional.empty();
        }
        factura.setTotal(getTotal(factura.getItems(), factura.getDescuento()));
        factura.setTotalIva(getTotalIva(factura.getItems(), factura.getDescuento()));

        return Optional.of(facturaMdbRepository.save(factura));
    }

    @Override
    public ResponseEntity delete(String id){
        Optional<Factura> factura = facturaMdbRepository.findById(id);
        if(!factura.isEmpty()){
            Optional<User> user = userMdbRepository.findById(factura.get().getUserId());
            if(!user.isEmpty()){
                if(user.get().getId().equals(factura.get().getUserId()) || user.get().getRole() == Role.ADMIN){
                    facturaMdbRepository.deleteById(id);
                    return new ResponseEntity(HttpStatus.OK);
                }
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    private Double getTotal(Item[] items, double descuento){
        double total = 0;
        double totalSinDescuento = 0;
        double totalConDescuento = 0;
        for(Item item : items){
            totalSinDescuento = item.getCantidad() * item.getPrecio();
            totalConDescuento = totalSinDescuento - ((totalSinDescuento * item.getDescuento())/ 100);
            total += totalConDescuento;
        }
        total = total - (total * descuento / 100) ;
        return total;
    }

    private Double getTotalIva(Item[] items, double descuento){
        double total = 0;
        double totalSinDescuento = 0;
        double totalConDescuento = 0;
        double totalConIva = 0;
        for(Item item : items){
            totalSinDescuento = item.getCantidad() * item.getPrecio();
            totalConDescuento = totalSinDescuento - ((totalSinDescuento *item.getDescuento())/ 100);
            totalConIva = totalConDescuento + ((totalConDescuento * item.getIva())/ 100);
            total += totalConIva;
        }
        total = total * (1 - descuento/100);
        return total;
    }

    private void setValues(Optional<Factura> factura, FacturaDto facturaDto){
        factura.get().setNombre(facturaDto.getNombre());
        factura.get().setItems(facturaDto.getItems());
        factura.get().setTotal(getTotal(facturaDto.getItems(), facturaDto.getDescuento()));
        factura.get().setTotalIva(getTotalIva(facturaDto.getItems(), facturaDto.getDescuento()));
        facturaMdbRepository.save(factura.get());
    }
}
