package connectroomies.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;
    private String telefono;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String estado;
    @Column(name = "fecha_reg")
    private LocalDateTime fechaRegistro;
    //MÉTODO PARA QUE LA FEHCA_REGISTRO SE COJA DE MANERA AUTOMÁTICA
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    //RELACIÓN CON VIVIENDAS
    @OneToMany(mappedBy = "propietario")
    private List<Vivienda> viviendas;


    //FALTA ROLES
    //private List<Rol> roles;
}