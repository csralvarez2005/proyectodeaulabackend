package inventarioEyBackend.service;

import inventarioEyBackend.model.EquipoMongo;

import java.util.List;

public interface EquipoMongoService {
    List<EquipoMongo> obtenerTodos();
    void guardar(EquipoMongo equipo);
}
