package inventarioEyBackend.service;

import inventarioEyBackend.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {
    List<Funcionario> getAllFuncionarios();
    Page<Funcionario> getAllFuncionarios(Pageable pageable);
    Optional<Funcionario> getFuncionarioById(Long id);
    Funcionario createFuncionario(Funcionario funcionario);
    Funcionario updateFuncionario(Long id, Funcionario funcionarioDetails);
    void deleteFuncionario(Long id);
    Optional<Funcionario> findByEmail(String email);
    List<Funcionario> findByCargo(String cargo);
    boolean existsById(Long id);
    boolean existsByIdentificacion(String identificacion);
}
