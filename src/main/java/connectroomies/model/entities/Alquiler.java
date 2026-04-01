package connectroomies.model.entities;

import java.time.LocalDateTime;

import connectroomies.model.enums.EstadoAlquiler;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "alquileres")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer duracionMeses;
    private String mensaje;

    @Enumerated(EnumType.STRING)
    private EstadoAlquiler estado;
    
    @ManyToOne
    @JoinColumn(name = "inquilino_id")
    private Usuario inquilino;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Usuario propietario;

    @ManyToOne
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;

    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;   

}
