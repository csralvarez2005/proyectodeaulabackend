package inventarioEyBackend.repository;

import inventarioEyBackend.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByEmail(String email);
    List<Funcionario> findByCargo(String cargo);
    boolean existsByIdentificacion(String identificacion);
}
