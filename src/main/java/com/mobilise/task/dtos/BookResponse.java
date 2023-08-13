package com.mobilise.task.dtos;

import com.mobilise.task.enums.AgeRating;
import com.mobilise.task.enums.Genre;
import com.mobilise.task.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookResponse {
    private String title;
    private String description;
    private int isbn;
    private int publicationYear;
    private long price;
    private LocalDate dateUploaded;
    private AgeRating ageRating;
    private Genre genre;
    private Language language;
    private String reference;
    private String url;

}
