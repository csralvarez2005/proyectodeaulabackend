package inventarioEyBackend.service.impl;

import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.model.Monitor;
import inventarioEyBackend.repository.EquipoRepository;
import inventarioEyBackend.repository.MonitorRepository;
import inventarioEyBackend.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public List<Monitor> getAllMonitores() {
        return monitorRepository.findAll();
    }

    @Override
    public Page<Monitor> getAllMonitores(Pageable pageable) {
        return monitorRepository.findAll(pageable);
    }

    @Override
    public Optional<Monitor> getMonitorById(Long id) {
        return monitorRepository.findById(id);
    }

    @Override
    public Monitor createMonitor(Monitor monitor) {
        return monitorRepository.save(monitor);
    }

    @Override
    public Monitor updateMonitor(Long id, Monitor monitorDetails) {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monitor no encontrado con id: " + id));

        monitor.setMarca(monitorDetails.getMarca());
        monitor.setModelo(monitorDetails.getModelo());
        monitor.setNumeroSerie(monitorDetails.getNumeroSerie());
        monitor.setTamanoPantalla(monitorDetails.getTamanoPantalla());
        monitor.setResolucion(monitorDetails.getResolucion());
        monitor.setTipoConector(monitorDetails.getTipoConector());
        monitor.setEstado(monitorDetails.getEstado());
        monitor.setObservaciones(monitorDetails.getObservaciones());
        monitor.setEsAjustable(monitorDetails.getEsAjustable());
        monitor.setTieneAltavoces(monitorDetails.getTieneAltavoces());

        return monitorRepository.save(monitor);
    }

    @Override
    public void deleteMonitor(Long id) {
        Monitor monitor = monitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monitor no encontrado con id: " + id));
        monitorRepository.delete(monitor);
    }

    @Override
    public List<Monitor> findByMarca(String marca) {
        return monitorRepository.findByMarca(marca);
    }

    @Override
    public List<Monitor> findByModelo(String modelo) {
        return monitorRepository.findByModelo(modelo);
    }

    @Override
    public List<Monitor> findByEstado(String estado) {
        return monitorRepository.findByEstado(estado);
    }

    @Override
    public boolean existsById(Long id) {
        return monitorRepository.existsById(id);
    }

    @Override
    public boolean existsByNumeroSerie(String numeroSerie) {
        return monitorRepository.existsByNumeroSerie(numeroSerie);
    }

    @Override
    @Transactional
    public Monitor asignarEquipo(Long monitorId, Long equipoId) {
        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Monitor no encontrado con id: " + monitorId));

        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + equipoId));

        monitor.setEquipo(equipo);
        return monitorRepository.save(monitor);
    }

    @Override
    @Transactional
    public Monitor desasignarEquipo(Long monitorId) {
        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Monitor no encontrado con id: " + monitorId));

        monitor.setEquipo(null);
        return monitorRepository.save(monitor);
    }

    @Override
    public List<Monitor> findByEquipoIsNull() {
        return monitorRepository.findByEquipoIsNull();
    }
}