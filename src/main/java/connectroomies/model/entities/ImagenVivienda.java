package connectroomies.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "imgs_vivienda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenVivienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_img", nullable = false)
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;
}