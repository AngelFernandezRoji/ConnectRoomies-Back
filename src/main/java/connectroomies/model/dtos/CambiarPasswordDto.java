package connectroomies.model.dtos;

import lombok.Data;

@Data
public class CambiarPasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}