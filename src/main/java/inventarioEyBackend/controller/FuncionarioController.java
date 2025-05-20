package inventarioEyBackend.controller;
import inventarioEyBackend.model.Funcionario;
import inventarioEyBackend.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*", maxAge = 3600)

public class FuncionarioController {
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Funcionario>> getAllFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.getAllFuncionarios();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Page<Funcionario>> getAllFuncionariosPaginated(Pageable pageable) {
        Page<Funcionario> funcionarios = funcionarioService.getAllFuncionarios(pageable);
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Funcionario> getFuncionarioById(@PathVariable Long id) {
        return funcionarioService.getFuncionarioById(id)
                .map(funcionario -> new ResponseEntity<>(funcionario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<Funcionario> getFuncionarioByEmail(@PathVariable String email) {
        return funcionarioService.findByEmail(email)
                .map(funcionario -> new ResponseEntity<>(funcionario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/cargo/{cargo}")
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    public ResponseEntity<List<Funcionario>> getFuncionariosByCargo(@PathVariable String cargo) {
        List<Funcionario> funcionarios = funcionarioService.findByCargo(cargo);
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createFuncionario(@RequestBody Funcionario funcionario) {
        if (funcionarioService.existsByIdentificacion(funcionario.getIdentificacion())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Ya existe un funcionario con esta identificación");
        }

        Funcionario newFuncionario = funcionarioService.createFuncionario(funcionario);
        return new ResponseEntity<>(newFuncionario, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioDetails) {
        if (!funcionarioService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Funcionario updatedFuncionario = funcionarioService.updateFuncionario(id, funcionarioDetails);
        return new ResponseEntity<>(updatedFuncionario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteFuncionario(@PathVariable Long id) {
        if (!funcionarioService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        funcionarioService.deleteFuncionario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
