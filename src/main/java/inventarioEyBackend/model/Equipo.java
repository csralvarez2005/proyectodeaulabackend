package inventarioEyBackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventario_equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipo_id")
    private Long id;

    // Información general
    @Column(length = 255, name = "codigo_equipo", unique = true, nullable = false)
    private String codigoEquipo;

    @Column(length = 255)
    private String descripcion;

    @Column(length = 255)
    private String tipo;

    @Column(length = 255)
    private String modelo;

    @Column(length = 255)
    private String marca;

    @Column(length = 255)
    private String serie;

    @Column(name = "ubicacion_del_equipo", length = 255)
    private String ubicacionDelEquipo;

    @Column(length = 255)
    private String utilizacion;

    @Column(name = "recibido_por", length = 255)
    private String recibidoPor;

    // Información de compra y garantía
    @Column(length = 255)
    private String proveedor;

    @Column(name = "orden_de_compra", length = 255)
    private String ordenDeCompra;

    @Column(length = 255)
    private String factura;

    @Column(name = "fecha_de_compra")
    private LocalDate fechaDeCompra;

    @Column(name = "fecha_fin_garantia")
    private LocalDate fechaFinGarantia;

    @Column(length = 255)
    private String garantia;

    @Positive(message = "El precio debe ser un valor positivo")
    private Double precio;

    // Hardware
    @Column(length = 255)
    private String procesador;

    private Integer memoriaRamGB;

    private Integer almacenamientoGB;

    @Column(length = 255)
    private String tipoAlmacenamiento;

    @Column(length = 255)
    private String placaBase;

    private Integer fuentePoderWatts;

    @Column(length = 255)
    private String tarjetaGrafica;

    private Boolean tieneTarjetaRed;

    private Boolean tieneTarjetaSonido;

    @Column(length = 255)
    private String gabinete;

    @Column(length = 255)
    private String perifericosEntrada;

    @Column(length = 255)
    private String perifericosSalida;

    @Column(length = 255)
    private String componentes;

    @Column(length = 255)
    private String accesorios;

    // Software
    @Column(length = 255)
    private String sistemaOperativo;

    @Column(length = 255)
    private String versionSO;

    @Column(length = 255)
    private String driversInstalados;

    @Column(length = 255)
    private String programasInstalados;

    @Column(length = 255)
    private String utilidadesSistema;

    // Red / Configuración técnica
    @Column(length = 255)
    private String direccionIP;

    @Column(length = 255)
    private String direccionMAC;

    @Column(length = 255)
    private String estado;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Monitor> monitores = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "impresora_id") // Esta columna se agregará en la tabla inventario_equipos
    private Impresora impresora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "equipo_area",
            joinColumns = @JoinColumn(name = "equipo_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<Area> areas = new ArrayList<>();
}
