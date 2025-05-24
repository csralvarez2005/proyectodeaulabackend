package inventarioEyBackend.service;

import inventarioEyBackend.model.Monitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MonitorService {
    List<Monitor> getAllMonitores();
    Page<Monitor> getAllMonitores(Pageable pageable);
    Optional<Monitor> getMonitorById(Long id);
    Monitor createMonitor(Monitor monitor);
    Monitor updateMonitor(Long id, Monitor monitorDetails);
    void deleteMonitor(Long id);
    List<Monitor> findByMarca(String marca);
    List<Monitor> findByModelo(String modelo);
    List<Monitor> findByEstado(String estado);
    boolean existsById(Long id);
    boolean existsByNumeroSerie(String numeroSerie);
    Monitor asignarEquipo(Long monitorId, Long equipoId);
    Monitor desasignarEquipo(Long monitorId);
    List<Monitor> findByEquipoIsNull();
}
