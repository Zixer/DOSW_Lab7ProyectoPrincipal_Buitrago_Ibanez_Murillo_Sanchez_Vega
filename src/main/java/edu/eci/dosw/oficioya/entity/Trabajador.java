package edu.eci.dosw.oficioya.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trabajadores")
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "trabajador", fetch = FetchType.LAZY)
    private List<DisponibilidadSemanal> disponibilidades = new ArrayList<>();

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(columnDefinition = "TEXT")
    private String fotoPerfilUrl;

    @Column(precision = 10, scale = 2)
    private BigDecimal tarifaAproximada;

    @Column(nullable = false)
    private Boolean disponibleAhora;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String estado;

    @Column
    private Double penalizacionAcumulada;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column
    private LocalDateTime fechaActualizacion;

    @Column(nullable = false)
    private Boolean eliminadoLogico;

    // Constructor vacÃo
    public Trabajador() {
    }

    // Constructor con atributos
    public Trabajador(Usuario usuario, String nombreCompleto,
                     String fotoPerfilUrl, BigDecimal tarifaAproximada,
                     Boolean disponibleAhora, String estado,
                     Double penalizacionAcumulada,
                     LocalDateTime fechaCreacion,
                     LocalDateTime fechaActualizacion,
                     Boolean eliminadoLogico) {

        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.tarifaAproximada = tarifaAproximada;
        this.disponibleAhora = disponibleAhora;
        this.estado = estado;
        this.penalizacionAcumulada = penalizacionAcumulada;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.eliminadoLogico = eliminadoLogico;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
    return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public BigDecimal getTarifaAproximada() {
        return tarifaAproximada;
    }

    public void setTarifaAproximada(BigDecimal tarifaAproximada) {
        this.tarifaAproximada = tarifaAproximada;
    }

    public Boolean getDisponibleAhora() {
        return disponibleAhora;
    }

    public void setDisponibleAhora(Boolean disponibleAhora) {
        this.disponibleAhora = disponibleAhora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPenalizacionAcumulada() {
        return penalizacionAcumulada;
    }

    public void setPenalizacionAcumulada(Double penalizacionAcumulada) {
        this.penalizacionAcumulada = penalizacionAcumulada;
    }

    public List<DisponibilidadSemanal> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(
            List<DisponibilidadSemanal> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Boolean getEliminadoLogico() {
        return eliminadoLogico;
    }

    public void setEliminadoLogico(Boolean eliminadoLogico) {
        this.eliminadoLogico = eliminadoLogico;
    }
}

