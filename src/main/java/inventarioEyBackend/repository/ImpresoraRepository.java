package inventarioEyBackend.repository;


import inventarioEyBackend.model.Impresora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpresoraRepository extends JpaRepository<Impresora, Long> {
    boolean existsByNumeroSerie(String numeroSerie);
}
