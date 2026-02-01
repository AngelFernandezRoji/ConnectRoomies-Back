package connectroomies.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "viviendas")
public class Vivienda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titulo;
	private String tipo;
	private String direccion;
	private String localidad;
	private String provincia;
	private String codigo_postal;
	private Double precio;
	private Integer disponible;
	private LocalDateTime fecha_creacion;
	//MÉTODO PARA QUE LA FEHCA_CREACION SE COJA DE MANERA AUTOMÁTICA
	@PrePersist
	protected void onCreate() {
	    fecha_creacion = LocalDateTime.now();
	}
	//RELACIÓN CON USUARIO (propietario)
	@ManyToOne
	@JoinColumn(name = "propietario_id")
	private Usuario propietario;
	//RELACIÓN CON HABITACIÓN
	@OneToMany(mappedBy = "vivienda")
	private List<Habitacion> habitaciones;

	

}
