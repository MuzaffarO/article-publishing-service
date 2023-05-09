package uz.nt.articlepublishingservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "image_id_seq")
    @SequenceGenerator(name = "image_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
    private Integer id;
    private String url;
    private Integer userId;
}
