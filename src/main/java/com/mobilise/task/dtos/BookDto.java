package com.mobilise.task.dtos;

import com.mobilise.task.enums.AgeRating;
import com.mobilise.task.enums.Genre;
import com.mobilise.task.enums.Language;
import com.mobilise.task.models.Book;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String title;
    private String description;
    private int isbn;
    private int publicationYear;
    private long price;
    private LocalDate dateUploaded;
    private AgeRating ageRating;
    private Genre genre;
    private Language language;
    private List<AuthorDto> authors;
    private String reference;
    private String url;

    public static BookDto fromModel(Book book){
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        bookDto.setAuthors(book.getAuthors().stream().map(author -> {
            AuthorDto authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            return authorDto;
        }).toList());
        return bookDto;
    }

    public static BookDto toDto(Object o){
        return fromModel((Book) o);
    }
}
