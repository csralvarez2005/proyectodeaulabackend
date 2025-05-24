package inventarioEyBackend.service;

import inventarioEyBackend.mapper.EquipoMapper;
import inventarioEyBackend.model.Equipo;
import inventarioEyBackend.model.EquipoMongo;
import inventarioEyBackend.repository.EquipoMongoRepository;
import inventarioEyBackend.repository.EquipoRepository;
import inventarioEyBackend.util.PdfGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class MigracionService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EquipoMongoRepository equipoMongoRepository;

    @PostConstruct
    public void migrarSiMongoEstaVacio() {
        if (equipoMongoRepository.count() == 0) {
            List<Equipo> equiposMySQL = equipoRepository.findAll();

            for (Equipo equipo : equiposMySQL) {
                EquipoMongo equipoMongo = EquipoMapper.toMongo(equipo);
                equipoMongoRepository.save(equipoMongo);
            }

            System.out.println("✅ Migración completa: " + equiposMySQL.size() + " equipos migrados a MongoDB.");
        } else {
            System.out.println("ℹ️ MongoDB ya contiene datos, no se ejecuta la migración.");
        }
    }
    public int migrarForzado() {
        List<Equipo> equiposMySQL = equipoRepository.findAll();

        for (Equipo equipo : equiposMySQL) {
            EquipoMongo equipoMongo = EquipoMapper.toMongo(equipo);
            equipoMongoRepository.save(equipoMongo);
        }

        return equiposMySQL.size();
    }
    public ByteArrayInputStream migrarYGenerarPdf() {
        List<Equipo> equiposMySQL = equipoRepository.findAll();

        for (Equipo equipo : equiposMySQL) {
            equipoMongoRepository.save(EquipoMapper.toMongo(equipo));
        }

        return PdfGenerator.generarReporteEquipos(equiposMySQL);
    }
}
