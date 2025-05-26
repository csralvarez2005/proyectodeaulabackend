package inventarioEyBackend.service.impl;

import inventarioEyBackend.dto.ReporteAsignacionDTO;
import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.mapper.AsignacionEquipoMapper;
import inventarioEyBackend.model.AsignacionEquipo;
import inventarioEyBackend.model.AsignacionEquipoMongo;
import inventarioEyBackend.repository.AsignacionEquipoMongoRepository;
import inventarioEyBackend.repository.AsignacionEquipoRepository;
import inventarioEyBackend.service.AsignacionEquipoService;
import inventarioEyBackend.util.PdfGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
@Service
public class AsignacionEquipoServiceImpl implements AsignacionEquipoService {
    @Autowired
    private AsignacionEquipoRepository asignacionRepository;

    @Autowired
    private AsignacionEquipoMongoRepository asignacionMongoRepository;

    // ============================
    // CRUD Methods
    // ============================
    @Override
    public List<AsignacionEquipo> getAllAsignaciones() {
        return asignacionRepository.findAll();
    }

    @Override
    public Page<AsignacionEquipo> getAllAsignaciones(Pageable pageable) {
        return asignacionRepository.findAll(pageable);
    }

    @Override
    public Optional<AsignacionEquipo> getAsignacionById(Long id) {
        return asignacionRepository.findById(id);
    }

    @Override
    @Transactional
    public AsignacionEquipo createAsignacion(AsignacionEquipo asignacion) {
        return asignacionRepository.save(asignacion);
    }

    @Override
    @Transactional
    public AsignacionEquipo updateAsignacion(Long id, AsignacionEquipo asignacionDetails) {
        return asignacionRepository.findById(id)
                .map(asignacion -> {
                    asignacion.setFechaAsignacion(asignacionDetails.getFechaAsignacion());
                    asignacion.setEquipo(asignacionDetails.getEquipo());
                    asignacion.setFuncionario(asignacionDetails.getFuncionario());
                    asignacion.setArea(asignacionDetails.getArea());
                    return asignacionRepository.save(asignacion);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public void deleteAsignacion(Long id) {
        AsignacionEquipo asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id: " + id));
        asignacionRepository.delete(asignacion);
    }

    @Override
    public boolean existsById(Long id) {
        return asignacionRepository.existsById(id);
    }

    @Override
    public List<AsignacionEquipo> getAsignacionesByEquipoId(Long equipoId) {
        return asignacionRepository.findByEquipoId(equipoId);
    }

    @Override
    public List<AsignacionEquipo> getAsignacionesByFuncionarioId(Long funcionarioId) {
        return asignacionRepository.findByFuncionarioId(funcionarioId);
    }

    @Override
    public List<AsignacionEquipo> getAsignacionesByAreaId(Long areaId) {
        return asignacionRepository.findByAreaId(areaId);
    }

    // ============================
    // Migración y PDF
    // ============================
    @PostConstruct
    @Override
    public void migrarSiMongoEstaVacio() {
        if (asignacionMongoRepository.count() == 0) {
            List<AsignacionEquipo> asignaciones = asignacionRepository.findAll();
            for (AsignacionEquipo asignacion : asignaciones) {
                asignacionMongoRepository.save(AsignacionEquipoMapper.toMongo(asignacion));
            }
            System.out.println("✅ Migración automática completa: " + asignaciones.size() + " asignaciones.");
        } else {
            System.out.println("ℹ️ MongoDB ya tiene asignaciones. No se migró.");
        }
    }

    @Override
    public int migrarForzado() {
        List<AsignacionEquipo> asignaciones = asignacionRepository.findAll();
        for (AsignacionEquipo asignacion : asignaciones) {
            asignacionMongoRepository.save(AsignacionEquipoMapper.toMongo(asignacion));
        }
        return asignaciones.size();
    }

    @Override
    public ByteArrayInputStream migrarYGenerarPdf() {
        List<AsignacionEquipo> asignaciones = asignacionRepository.findAll();
        for (AsignacionEquipo asignacion : asignaciones) {
            asignacionMongoRepository.save(AsignacionEquipoMapper.toMongo(asignacion));
        }

        List<ReporteAsignacionDTO> datosReporte = asignacionRepository.obtenerReporteAsignaciones();
        return PdfGenerator.generarReporteAsignaciones(datosReporte);
    }
}
