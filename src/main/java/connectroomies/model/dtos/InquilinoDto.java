package connectroomies.model.dtos;

import lombok.Data;

@Data
public class InquilinoDto {
	private Long id;
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
}
