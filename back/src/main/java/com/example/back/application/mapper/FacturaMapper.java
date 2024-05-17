package com.example.back.application.mapper;

import com.example.back.application.dto.FacturaDto;
import com.example.back.domain.entity.Factura;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacturaMapper extends EntityMapper<FacturaDto, Factura>{
}
