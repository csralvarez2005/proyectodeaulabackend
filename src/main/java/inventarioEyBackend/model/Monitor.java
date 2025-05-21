package inventarioEyBackend.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventario_monitores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monitor_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String marca;

    @Column(length = 50, nullable = false)
    private String modelo;

    @Column(length = 20, nullable = false)
    private String numeroSerie;
    @Column(name = "orden_de_compra", length = 255)
    private String ordenDeCompra;

    @Column(length = 20)
    private String tamanoPantalla;

    @Column(length = 50)
    private String resolucion;

    @Column(length = 30)
    private String tipoConector;

    @Column(length = 20)
    private String estado;

    @Column(length = 255)
    private String observaciones;

    @Column
    private Boolean esAjustable;

    @Column
    private Boolean tieneAltavoces;

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

    @Column(name = "recibido_por", length = 255)
    private String recibidoPor;

    // Información de compra y garantía
    @Column(length = 255)
    private String proveedor;
}