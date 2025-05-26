package inventarioEyBackend.dto;

import java.time.LocalDate;

public interface ReporteAsignacionDTO {
    String getNombreArea();
    String getCodigoEquipo();
    String getFuncionario();
    LocalDate getFechaAsignacion();
}
