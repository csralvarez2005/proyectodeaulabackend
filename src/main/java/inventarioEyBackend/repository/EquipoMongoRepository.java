package inventarioEyBackend.repository;

import inventarioEyBackend.model.EquipoMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipoMongoRepository extends MongoRepository<EquipoMongo, String> {
}
