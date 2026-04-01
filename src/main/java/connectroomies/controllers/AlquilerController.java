package connectroomies.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import connectroomies.model.dtos.AlquilerDto;
import connectroomies.model.dtos.AlquilerResponseDto;
import connectroomies.model.dtos.RegistrarAlquilerRequestDto;
import connectroomies.model.entities.Usuario;
import connectroomies.model.enums.EstadoAlquiler;
import connectroomies.model.mappers.AlquilerMapper;
import connectroomies.security.MyUserDetails;
import connectroomies.services.AlquilerService;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alquileres")
public class AlquilerController {
    
    private final AlquilerService alquilerService;

    @PostMapping
    public ResponseEntity<AlquilerResponseDto> crearSolicitud(
            @RequestBody RegistrarAlquilerRequestDto req,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();

        AlquilerResponseDto response = alquilerService.crearSolicitud(req, usuario);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AlquilerDto>> getTodas() {
        return ResponseEntity.ok(
                alquilerService.findAll()
                        .stream()
                        .map(AlquilerMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/propietario")
    public ResponseEntity<List<AlquilerDto>> getByPropietario(
            @RequestParam Long propietarioId) {

        return ResponseEntity.ok(
                alquilerService.getSolicitudesPropietario(propietarioId)
        );
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        EstadoAlquiler estado = EstadoAlquiler.valueOf(body.get("estado"));

        alquilerService.actualizarEstado(id, estado);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AlquilerDto>> getByUsuario(
            @PathVariable Long usuarioId) {

        return ResponseEntity.ok(
                alquilerService.getAlquileresByUsuario(usuarioId)
        );
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarAlquiler(
            @PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();

        alquilerService.cancelarAlquiler(id, usuario);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        Usuario usuario = userDetails.getUsuario();

        alquilerService.deleteAlquiler(id, usuario);

        return ResponseEntity.noContent().build();
    }
}
