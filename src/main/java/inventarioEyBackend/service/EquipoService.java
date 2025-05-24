package inventarioEyBackend.service;
import inventarioEyBackend.model.Area;
import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.model.Monitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
public interface EquipoService {

    List<Equipo> getAllEquipos();
    Page<Equipo> getAllEquipos(Pageable pageable);
    Optional<Equipo> getEquipoById(Long id);
    Equipo createEquipo(Equipo equipo);
    Equipo updateEquipo(Long id, Equipo equipoDetails);
    void deleteEquipo(Long id);
    List<Equipo> findByTipo(String tipo);
    List<Equipo> findByMarca(String marca);
    List<Equipo> findByEstado(String estado);
    boolean existsById(Long id);
    Equipo agregarMonitor(Long equipoId, Long monitorId);
    Equipo quitarMonitor(Long equipoId, Long monitorId);
    List<Equipo> findByMonitoresIsEmpty();
    List<Monitor> getMonitoresByEquipoId(Long equipoId);
    List<Area> getAreasByEquipoId(Long equipoId);
    String generarCodigoEquipo(String abreviatura);
}
