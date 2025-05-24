package inventarioEyBackend.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "equipo")
public class EquipoMongo {

    @Id
    private String id;

    private String codigoEquipo;
    private String descripcion;
    private String tipo;
    private String modelo;
    private String marca;
    private String serie;
    private String ubicacionDelEquipo;
    private String utilizacion;
    private String recibidoPor;

    private String proveedor;
    private String ordenDeCompra;
    private String factura;
    private LocalDate fechaDeCompra;
    private LocalDate fechaFinGarantia;
    private String garantia;
    private Double precio;

    private String procesador;
    private Integer memoriaRamGB;
    private Integer almacenamientoGB;
    private String tipoAlmacenamiento;
    private String placaBase;
    private Integer fuentePoderWatts;
    private String tarjetaGrafica;
    private Boolean tieneTarjetaRed;
    private Boolean tieneTarjetaSonido;
    private String gabinete;
    private String perifericosEntrada;
    private String perifericosSalida;
    private String componentes;
    private String accesorios;

    private String sistemaOperativo;
    private String versionSO;
    private String driversInstalados;
    private String programasInstalados;
    private String utilidadesSistema;

    private String direccionIP;
    private String direccionMAC;
    private String estado;

    private String area; // nombre del área
    private String impresora; // nombre o ID de la impresora
}
