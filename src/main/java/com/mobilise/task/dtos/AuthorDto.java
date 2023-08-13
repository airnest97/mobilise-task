package com.mobilise.task.dtos;

import com.mobilise.task.models.Author;
import lombok.*;
import org.springframework.beans.BeanUtils;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String bio;
    private String reference;


    public static AuthorDto fromModel(Author author){
        AuthorDto authorDto = new AuthorDto();
        BeanUtils.copyProperties(author,authorDto);
        return authorDto;
    }


    public static AuthorDto toDto(Object o) {
        return fromModel((Author) o);
    }
}
