package edu.eci.dosw.oficioya.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DisponibilidadSemanalMapper {

    @Mapping(source = "activa", target = "activo")
    @Mapping(target = "trabajador", ignore = true)
    edu.eci.dosw.oficioya.entity.DisponibilidadSemanal toEntity(
        edu.eci.dosw.oficioya.model.DisponibilidadSemanal model
    );

    @Mapping(source = "activo", target = "activa")
    @Mapping(target = "trabajador", ignore = true)
    edu.eci.dosw.oficioya.model.DisponibilidadSemanal toModel(
        edu.eci.dosw.oficioya.entity.DisponibilidadSemanal entity
    );
}