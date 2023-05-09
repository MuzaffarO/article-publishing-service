package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table()
@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "image_id_seq")
    @SequenceGenerator(name = "image_id_seq", sequenceName = "image_id_seq", allocationSize = 1)
    private Integer id;
    private Integer userId;
    private String pathSmall;
    private String pathMedium;
    private String pathLarge;
    private String ext;

}
