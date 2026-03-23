package connectroomies.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
	@Column(name = "codigo_postal")
	private String codigoPostal;
	private Double precio;
	private Integer disponible;
	@Column(name="fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	//MÉTODO PARA QUE LA FEHCA_CREACION SE COJA DE MANERA AUTOMÁTICA
	@PrePersist
	protected void onCreate() {
	    fechaCreacion = LocalDateTime.now();
	}
	
	//RELACIÓN CON USUARIO (propietario)
	@ManyToOne
	@JoinColumn(name = "propietario_id")
	private Usuario propietario;
	
	//RELACIÓN CON HABITACIÓN
	@OneToMany(mappedBy = "vivienda")
	@JsonIgnore
	private List<Habitacion> habitaciones;

	

}
