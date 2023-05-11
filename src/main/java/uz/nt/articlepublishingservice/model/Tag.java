package uz.nt.articlepublishingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(generator = "tag_id")
    @SequenceGenerator(name = "tag_id", sequenceName = "tag_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer usage_count;
    public Tag(String name){
        this.name = name;
    }
}
