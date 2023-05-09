package uz.nt.articlepublishingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Tag {
    @Id
    @GeneratedValue(generator = "tag_id")
    @SequenceGenerator(name = "tag_id", sequenceName = "tag_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer numOfUsage;
}
