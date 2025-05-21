package inventarioEyBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "impresoras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Impresora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_impresora")
    private Long idImpresora;

    @Column(length = 50, nullable = false)
    private String numeroSerie;

    @Column(length = 50)
    private String codigoPatrimonial;

    @Column(length = 50)
    private String marca;

    @Column(length = 50)
    private String modelo;

    @Column(length = 50)
    private String tipoImpresora; // inyección de tinta, láser, etc.

    @Column(length = 50)
    private String tecnologiaImpresion; // Color / Blanco y negro / Ambas, matrix

    @Column(length = 50)
    private String tipoConexion; // USB, Ethernet, Wi-Fi, Bluetooth

    @Column
    private Boolean tieneEscaner;

    @Column
    private Boolean tieneWifi;

    @Column
    private Boolean tieneDuplex;

    @Column(length = 20)
    private String estadoFisico; // Bueno, Regular, Dañado

    @Column(length = 20)
    private String condicion; // Nueva, Usada, Reacondicionada

    @Column(length = 100)
    private String asignadoA; // funcionario o usuario asignado

    @Column(length = 100)
    private String areaDepartamento;

    @Column(length = 100)
    private String ubicacionFisica;

    @Column
    private LocalDate fechaAdquisicion;

    @Column(length = 100)
    private String proveedor;

    @Column
    private Double precio;

    @Column(length = 50)
    private String numeroFactura;

    @Column
    private Boolean garantiaVigente;

    @Column
    private LocalDate fechaVencimientoGarantia;

    @Column(length = 255)
    private String observaciones;

    @Column(length = 100)
    private String recibidoPor;
}