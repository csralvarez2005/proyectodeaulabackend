package inventarioEyBackend.service.impl;


import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.mapper.EquipoMapper;
import inventarioEyBackend.model.Area;
import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.model.Monitor;
import inventarioEyBackend.repository.AreaRepository;
import inventarioEyBackend.repository.EquipoMongoRepository;
import inventarioEyBackend.repository.EquipoRepository;
import inventarioEyBackend.repository.MonitorRepository;
import inventarioEyBackend.service.EquipoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private MonitorRepository monitorRepository;

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private EquipoMongoRepository equipoMongoRepository;

    @Override
    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Page<Equipo> getAllEquipos(Pageable pageable) {
        return equipoRepository.findAll(pageable);
    }

    @Override
    public Optional<Equipo> getEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    @Override
    @Transactional
    public Equipo createEquipo(Equipo equipo) {
        if (equipo.getAreas() == null || equipo.getAreas().isEmpty()) {
            throw new IllegalArgumentException("El equipo debe estar asociado a al menos un área");
        }

        Area areaPrincipal = equipo.getAreas().get(0);
        String abreviatura = areaPrincipal.getAbreviatura();
        String nuevoCodigo = generarCodigoEquipo(abreviatura);
        equipo.setCodigoEquipo(nuevoCodigo);

        if (equipo.getMonitores() != null) {
            for (Monitor monitor : equipo.getMonitores()) {
                monitor.setEquipo(equipo);
            }
        }

        Equipo equipoGuardado = equipoRepository.save(equipo);

        // Sincronizar con MongoDB
        equipoMongoRepository.save(EquipoMapper.toMongo(equipoGuardado));

        return equipoGuardado;
    }

    @Override
    @Transactional
    public Equipo updateEquipo(Long id, Equipo equipoDetails) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + id));

        equipo.setCodigoEquipo(equipoDetails.getCodigoEquipo());
        equipo.setMarca(equipoDetails.getMarca());
        equipo.setModelo(equipoDetails.getModelo());
        equipo.setTipo(equipoDetails.getTipo());
        equipo.setEstado(equipoDetails.getEstado());

        if (equipoDetails.getMonitores() != null) {
            equipo.getMonitores().clear();
            for (Monitor monitor : equipoDetails.getMonitores()) {
                monitor.setEquipo(equipo);
                equipo.getMonitores().add(monitor);
            }
        }

        Equipo equipoActualizado = equipoRepository.save(equipo);

        // Sincronizar con MongoDB
        equipoMongoRepository.save(EquipoMapper.toMongo(equipoActualizado));

        return equipoActualizado;
    }

    @Override
    public void deleteEquipo(Long id) {
        if (!equipoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipo no encontrado con id: " + id);
        }
        equipoRepository.deleteById(id);
    }

    @Override
    public List<Equipo> findByTipo(String tipo) {
        return equipoRepository.findByTipo(tipo);
    }

    @Override
    public List<Equipo> findByMarca(String marca) {
        return equipoRepository.findByMarca(marca);
    }

    @Override
    public List<Equipo> findByEstado(String estado) {
        return equipoRepository.findByEstado(estado);
    }

    @Override
    public boolean existsById(Long id) {
        return equipoRepository.existsById(id);
    }

    @Override
    @Transactional
    public Equipo agregarMonitor(Long equipoId, Long monitorId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + equipoId));

        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Monitor no encontrado con id: " + monitorId));

        // Verificar si el monitor ya está asignado a otro equipo
        if (monitor.getEquipo() != null && !monitor.getEquipo().getId().equals(equipoId)) {
            throw new IllegalStateException("El monitor ya está asignado a otro equipo");
        }

        monitor.setEquipo(equipo);
        equipo.getMonitores().add(monitor);

        return equipoRepository.save(equipo);
    }

    @Override
    @Transactional
    public Equipo quitarMonitor(Long equipoId, Long monitorId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + equipoId));

        Monitor monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Monitor no encontrado con id: " + monitorId));

        // Verificar que el monitor esté asignado a este equipo
        if (monitor.getEquipo() == null || !monitor.getEquipo().getId().equals(equipoId)) {
            throw new IllegalStateException("El monitor no está asignado a este equipo");
        }

        monitor.setEquipo(null);
        equipo.getMonitores().removeIf(m -> m.getId().equals(monitorId));

        return equipoRepository.save(equipo);
    }

    @Override
    public List<Equipo> findByMonitoresIsEmpty() {
        return equipoRepository.findByMonitoresIsEmpty();
    }

    @Override
    public List<Monitor> getMonitoresByEquipoId(Long equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + equipoId));
        return equipo.getMonitores();
    }

    @Override
    public List<Area> getAreasByEquipoId(Long equipoId) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con id: " + equipoId));

        if (equipo.getAreas() == null) {
            return new ArrayList<>();
        }

        return equipo.getAreas();
    }

    @Override
    public String generarCodigoEquipo(String abreviatura) {
        List<String> codigos = equipoRepository.findCodigosByAbreviaturaOrderedDesc(abreviatura);
        int siguienteNumero = 1;

        if (!codigos.isEmpty()) {
            String ultimoCodigo = codigos.get(0); // ej. "EY-SIS-0005"
            String numeroStr = ultimoCodigo.substring(ultimoCodigo.lastIndexOf("-") + 1);
            siguienteNumero = Integer.parseInt(numeroStr) + 1;
        }

        String numeroFormateado = String.format("%04d", siguienteNumero);
        return "EY-" + abreviatura + "-" + numeroFormateado; // Ej: EY-SIS-0006
    }


}