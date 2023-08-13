package com.mobilise.task.dtos;


import com.mobilise.task.enums.AgeRating;
import com.mobilise.task.enums.Genre;
import com.mobilise.task.enums.Language;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private int isbn;
    @NotBlank
    @Min(value = 1800, message = "Publication year can not be earlier than 1800")
    private int publicationYear;
    @Min(value = 1, message = "Price can not be less than 1")
    private long price;
    private String dateOfUpload;
    @NotBlank
    private AgeRating ageRating;
    @NotBlank
    private Genre genre;
    @NotBlank
    private Language language;
    private String authorList;
    private MultipartFile file;
}
