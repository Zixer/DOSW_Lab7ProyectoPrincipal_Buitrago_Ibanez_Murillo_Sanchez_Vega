# DOSW_Lab7_Buitrago_Ibanez_Murillo_Sanchez_Vega

### Diagrama entidad-relación (ER)

El siguiente diagrama representa la estructura de las entidades seleccionadas y las relaciones entre ellas.

![Diagrama entidad-relación del proyecto OficioYa](src/main/resources/docs/images/Diagrama%20ER%20Proyecto.png)

## Selección y justificación de entidades

Para implementar la persistencia relacional mediante **JPA y PostgreSQL** en el proyecto **OficioYa**, se seleccionaron las entidades `Usuario`, `Trabajador` y `DisponibilidadSemanal`, debido a su importancia en las funcionalidades principales del sistema.

### Entidades seleccionadas

| Entidad | Descripción | Funcionalidad principal |
|---|---|---|
| `Usuario` | Almacena la información necesaria para la autenticación, incluyendo el correo electrónico, la contraseña cifrada y el estado de la cuenta. | Autenticación y gestión de usuarios. |
| `Trabajador` | Contiene la información específica de los trabajadores registrados en la plataforma. | Creación, consulta y actualización de trabajadores (CRUD). |
| `DisponibilidadSemanal` | Gestiona los horarios en los que cada trabajador puede prestar sus servicios. | Administración de la disponibilidad horaria. |

### Relaciones entre entidades

Las entidades seleccionadas permiten establecer las siguientes relaciones:

- **Usuario – Trabajador (1...0/1):** cada trabajador está asociado a un usuario de la plataforma.
- **Trabajador – DisponibilidadSemanal (1...*):** un trabajador puede tener múltiples registros de disponibilidad semanal.

### Justificación de la selección

La selección de estas entidades permite cubrir los requisitos de **autenticación, gestión de trabajadores y establecimiento de relaciones entre entidades** exigidos en el Laboratorio 7. Además, aprovecha las funcionalidades desarrolladas previamente en OficioYa y permite integrar la persistencia de datos mediante JPA y PostgreSQL de manera estructurada y coherente con la arquitectura del proyecto.