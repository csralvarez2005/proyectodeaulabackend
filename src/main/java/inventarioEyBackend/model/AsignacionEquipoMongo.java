package inventarioEyBackend.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "asignaciones_equipos")
@Data
public class AsignacionEquipoMongo {
    @Id
    private String id;

    private LocalDate fechaAsignacion;
    private String areaNombre;
    private String equipoNombre;
    private String funcionarioNombre;
}
