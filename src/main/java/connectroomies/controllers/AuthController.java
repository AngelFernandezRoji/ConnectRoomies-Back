package connectroomies.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.LoginRequestDto;
import connectroomies.model.dtos.LoginResponseDto;
import connectroomies.model.entities.Usuario;
import connectroomies.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        Usuario usuario = usuarioService.findByEmail(request.getEmail());
        
        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }

        String rol = usuario.getRoles().stream()
                .findFirst()
                .map(r -> r.getNombre())
                .orElse("ARRENDATARIO");
        
        LoginResponseDto response = new LoginResponseDto(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getEmail(),
            rol
        );

        return ResponseEntity.ok(response);
    }
}