package inventarioEyBackend.service.impl;

import inventarioEyBackend.model.EquipoMongo;
import inventarioEyBackend.repository.EquipoMongoRepository;
import inventarioEyBackend.service.EquipoMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EquipoMongoServiceImpl implements EquipoMongoService {
    private final EquipoMongoRepository repository;

    @Autowired
    public EquipoMongoServiceImpl(EquipoMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EquipoMongo> obtenerTodos() {
        return repository.findAll();
    }

    @Override
    public void guardar(EquipoMongo equipo) {
        repository.save(equipo);
    }
}
