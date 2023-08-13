package com.mobilise.task.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Author")
@AllArgsConstructor
@NoArgsConstructor
public class Author extends AppendableReference {
    @Column(nullable = false, length = 10)
    private String firstName;
    @Column(length = 10)
    private String middleName;
    @Column(nullable = false, length = 10)
    private String lastName;
    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String bio;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
