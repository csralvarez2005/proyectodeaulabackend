package inventarioEyBackend.service;

import inventarioEyBackend.model.AsignacionEquipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AsignacionEquipoService {
    List<AsignacionEquipo> getAllAsignaciones();
    Page<AsignacionEquipo> getAllAsignaciones(Pageable pageable);
    Optional<AsignacionEquipo> getAsignacionById(Long id);
    AsignacionEquipo createAsignacion(AsignacionEquipo asignacion);
    AsignacionEquipo updateAsignacion(Long id, AsignacionEquipo asignacionDetails);
    void deleteAsignacion(Long id);
    List<AsignacionEquipo> getAsignacionesByEquipoId(Long equipoId);
    List<AsignacionEquipo> getAsignacionesByFuncionarioId(Long funcionarioId);
    List<AsignacionEquipo> getAsignacionesByAreaId(Long areaId);
    boolean existsById(Long id);
}
