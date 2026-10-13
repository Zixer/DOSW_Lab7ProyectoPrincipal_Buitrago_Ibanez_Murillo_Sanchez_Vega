package edu.eci.dosw.oficioya.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UsuarioMapper {

    // Convertir modelo de negocio a entidad JPA
    @Mapping(source = "correo", target = "email")
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "trabajador", ignore = true)
    @Mapping(target = "estadoCuenta", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "ultimoAcceso", ignore = true)
    @Mapping(target = "eliminadoLogico", ignore = true)
    edu.eci.dosw.oficioya.entity.Usuario toEntity(
        edu.eci.dosw.oficioya.model.Usuario model
    );

    // Convertir entidad JPA a modelo de negocio
    @Mapping(source = "email", target = "correo")
    @Mapping(target = "contrasena", ignore = true)
    @Mapping(target = "roles", ignore = true)
    edu.eci.dosw.oficioya.model.Usuario toModel(
        edu.eci.dosw.oficioya.entity.Usuario entity
    );
}