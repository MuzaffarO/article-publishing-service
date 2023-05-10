package uz.nt.articlepublishingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesDto {
    private Integer id;
    private String title;
    private String about;
    private String body;
    private UsersDto author;
    private Set<TagDto> tags;
    private Integer likes;

}
