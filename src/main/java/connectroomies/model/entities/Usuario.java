package connectroomies.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import connectroomies.model.enums.EstadoAlquiler;
import connectroomies.model.enums.EstadoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;
    @Column(name = "fecha_reg")
    private LocalDateTime fechaRegistro;
    
    //MÉTODO PARA QUE LA FEHCA_REGISTRO SE COJA DE MANERA AUTOMÁTICA
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }

    //RELACIÓN CON VIVIENDAS
    @OneToMany(mappedBy = "propietario")
    @JsonIgnore
    @Builder.Default
    private List<Vivienda> viviendas = new ArrayList<>();

    //RELACIÓN CON ROLES
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private List<Rol> roles = new ArrayList<>();
}