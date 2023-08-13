package com.mobilise.task.models;


import com.mobilise.task.enums.AgeRating;
import com.mobilise.task.enums.Genre;
import com.mobilise.task.enums.Language;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Book")
public class Book extends IAppendableReference{
    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false, unique = true)
    @Min(value = 1, message = "Please enter an isbn")
    private int isbn;
    @Column(nullable = false)
    @Min(1800)
    private int publicationYear;

    @Column(nullable = false)
    private long price;
    @Column(nullable = false)
    private LocalDate dateUploaded;
    private String url;
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Language language;
    private String fileName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
