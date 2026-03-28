package connectroomies.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {

	private Long id;
	private String nombre;
	private String email;
	private String rol;
}
