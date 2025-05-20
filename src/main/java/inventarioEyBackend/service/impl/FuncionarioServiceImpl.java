package inventarioEyBackend.service.impl;

import inventarioEyBackend.exception.ResourceNotFoundException;
import inventarioEyBackend.model.Funcionario;
import inventarioEyBackend.repository.FuncionarioRepository;
import inventarioEyBackend.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public List<Funcionario> getAllFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @Override
    public Page<Funcionario> getAllFuncionarios(Pageable pageable) {
        return funcionarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Funcionario> getFuncionarioById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Funcionario createFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @Override
    @Transactional
    public Funcionario updateFuncionario(Long id, Funcionario funcionarioDetails) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionario.setNombreFuncionario(funcionarioDetails.getNombreFuncionario());
                    funcionario.setApellidoFuncionario(funcionarioDetails.getApellidoFuncionario());
                    funcionario.setTipoDocumento(funcionarioDetails.getTipoDocumento());
                    funcionario.setIdentificacion(funcionarioDetails.getIdentificacion());
                    funcionario.setCargo(funcionarioDetails.getCargo());
                    funcionario.setEmail(funcionarioDetails.getEmail());
                    funcionario.setCelular(funcionarioDetails.getCelular());
                    funcionario.setDireccion(funcionarioDetails.getDireccion());
                    funcionario.setFechaNacimiento(funcionarioDetails.getFechaNacimiento());
                    funcionario.setGenero(funcionarioDetails.getGenero());
                    funcionario.setEstadoCivil(funcionarioDetails.getEstadoCivil());
                    funcionario.setEstado(funcionarioDetails.getEstado());
                    return funcionarioRepository.save(funcionario);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public void deleteFuncionario(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario no encontrado con id: " + id));
        funcionarioRepository.delete(funcionario);
    }

    @Override
    public Optional<Funcionario> findByEmail(String email) {
        return funcionarioRepository.findByEmail(email);
    }

    @Override
    public List<Funcionario> findByCargo(String cargo) {
        return funcionarioRepository.findByCargo(cargo);
    }

    @Override
    public boolean existsById(Long id) {
        return funcionarioRepository.existsById(id);
    }

    @Override
    public boolean existsByIdentificacion(String identificacion) {
        return funcionarioRepository.existsByIdentificacion(identificacion);
    }
}
