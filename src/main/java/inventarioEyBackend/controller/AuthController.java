package inventarioEyBackend.controller;

import inventarioEyBackend.dto.request.LoginRequest;
import inventarioEyBackend.dto.request.SignupRequest;
import inventarioEyBackend.dto.response.JwtResponse;
import inventarioEyBackend.dto.response.MessageResponse;
import inventarioEyBackend.model.ERole;
import inventarioEyBackend.model.Role;
import inventarioEyBackend.model.User;

import inventarioEyBackend.repository.RoleRepository;
import inventarioEyBackend.repository.UserRepository;
import inventarioEyBackend.security.jwt.JwtUtils;
import inventarioEyBackend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ¡El nombre de usuario ya está en uso!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ¡El email ya está en uso!"));
        }

        // Crear nueva cuenta de usuario
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        // Si es el primer usuario, asignar rol ADMIN
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(adminRole);
        } else {
            // Para los siguientes usuarios, asignar roles según la solicitud
            if (strRoles == null || strRoles.isEmpty()) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USUARIO)
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                            roles.add(adminRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USUARIO)
                                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                            roles.add(userRole);
                    }
                });
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("¡Usuario registrado exitosamente!"));
    }
}
