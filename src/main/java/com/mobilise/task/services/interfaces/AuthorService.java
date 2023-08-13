package com.mobilise.task.services.interfaces;

import com.mobilise.task.dtos.*;
import com.mobilise.task.models.Author;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface AuthorService {
    Author createAuthor(AuthorRequest author);
    void save(Author author);
    AuthorResponse findById(long id);
    void updateAuthor(long id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException;
    PagedResponse findAll(int pageNumber, int noOfItems);
    PageDto searchCriteria(String query, int page, int size);
    void deleteAuthor(long id);
    Optional<Author> findByEmail(String email);
}
