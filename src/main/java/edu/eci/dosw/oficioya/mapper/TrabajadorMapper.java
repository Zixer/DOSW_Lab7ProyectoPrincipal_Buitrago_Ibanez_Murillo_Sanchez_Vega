package edu.eci.dosw.oficioya.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = {UsuarioMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TrabajadorMapper {

    // Convertir modelo de negocio a entidad JPA
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "disponibilidades", ignore = true)
    @Mapping(target = "estado", ignore = true)
    edu.eci.dosw.oficioya.entity.Trabajador toEntity(
        edu.eci.dosw.oficioya.model.Trabajador model
    );

    // Convertir entidad JPA a modelo de negocio
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "horarios", ignore = true)
    @Mapping(target = "oficios", ignore = true)
    edu.eci.dosw.oficioya.model.Trabajador toModel(
        edu.eci.dosw.oficioya.entity.Trabajador entity
    );
}