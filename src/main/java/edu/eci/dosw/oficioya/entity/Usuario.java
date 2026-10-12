package edu.eci.dosw.oficioya.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(length = 20)
    private String telefono;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String estadoCuenta;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column
    private LocalDateTime ultimoAcceso;

    @Column(nullable = false)
    private Boolean eliminadoLogico;

    // Constructor vacío obligatorio para JPA
    public Usuario() {
    }

    // Constructor con atributos
    public Usuario(String email, String passwordHash,
                   String telefono, String estadoCuenta,
                   LocalDateTime fechaRegistro,
                   LocalDateTime ultimoAcceso,
                   Boolean eliminadoLogico) {

        this.email = email;
        this.passwordHash = passwordHash;
        this.telefono = telefono;
        this.estadoCuenta = estadoCuenta;
        this.fechaRegistro = fechaRegistro;
        this.ultimoAcceso = ultimoAcceso;
        this.eliminadoLogico = eliminadoLogico;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public Boolean getEliminadoLogico() {
        return eliminadoLogico;
    }

    public void setEliminadoLogico(Boolean eliminadoLogico) {
        this.eliminadoLogico = eliminadoLogico;
    }
}