package uz.nt.articlepublishingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.nt.articlepublishingservice.model.Tag;
import uz.nt.articlepublishingservice.model.Users;

import java.util.*;

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
    private Set<String> tags;
    private List<UsersDto> likes;

}
