package inventarioEyBackend.repository;

import inventarioEyBackend.model.AsignacionEquipoMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsignacionEquipoMongoRepository extends MongoRepository<AsignacionEquipoMongo, String> {
}
