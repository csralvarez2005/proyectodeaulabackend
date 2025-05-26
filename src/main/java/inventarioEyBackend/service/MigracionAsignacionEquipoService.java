package inventarioEyBackend.service;

import inventarioEyBackend.dto.ReporteAsignacionDTO;
import inventarioEyBackend.mapper.AsignacionEquipoMapper;
import inventarioEyBackend.model.AsignacionEquipo;
import inventarioEyBackend.model.AsignacionEquipoMongo;
import inventarioEyBackend.repository.AsignacionEquipoMongoRepository;
import inventarioEyBackend.repository.AsignacionEquipoRepository;
import inventarioEyBackend.util.PdfGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class MigracionAsignacionEquipoService {
    @Autowired
    private AsignacionEquipoRepository asignacionEquipoRepository;

    @Autowired
    private AsignacionEquipoMongoRepository asignacionEquipoMongoRepository;

    @PostConstruct
    public void migrarSiMongoEstaVacio() {
        if (asignacionEquipoMongoRepository.count() == 0) {
            List<AsignacionEquipo> asignaciones = asignacionEquipoRepository.findAll();

            for (AsignacionEquipo asignacion : asignaciones) {
                AsignacionEquipoMongo mongo = AsignacionEquipoMapper.toMongo(asignacion);
                asignacionEquipoMongoRepository.save(mongo);
            }

            System.out.println("✅ Migración automática de asignaciones completa: " + asignaciones.size() + " registros.");
        } else {
            System.out.println("ℹ️ MongoDB ya contiene asignaciones. No se migró.");
        }
    }

    public int migrarForzado() {
        List<AsignacionEquipo> asignaciones = asignacionEquipoRepository.findAll();

        for (AsignacionEquipo asignacion : asignaciones) {
            asignacionEquipoMongoRepository.save(AsignacionEquipoMapper.toMongo(asignacion));
        }

        return asignaciones.size();
    }

    public ByteArrayInputStream migrarYGenerarPdf() {
        List<AsignacionEquipo> asignaciones = asignacionEquipoRepository.findAll();

        for (AsignacionEquipo asignacion : asignaciones) {
            asignacionEquipoMongoRepository.save(AsignacionEquipoMapper.toMongo(asignacion));
        }

        List<ReporteAsignacionDTO> datosReporte = asignacionEquipoRepository.obtenerReporteAsignaciones();
        return PdfGenerator.generarReporteAsignaciones(datosReporte);
    }
}
