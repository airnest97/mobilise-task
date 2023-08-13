package com.mobilise.task.services.interfaces;

import com.mobilise.task.dtos.*;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.models.Author;
import com.mobilise.task.specifications.AuthorSpecs;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public interface AuthorService {
    Author createAuthor(AuthorRequest author) throws GenericException;
    void save(Author author);
    AuthorResponse findById(long id) throws GenericException;
    void updateAuthor(long id, AuthorRequest updateAuthorRequest) throws GenericException, InvocationTargetException, IllegalAccessException;
    Map<String, Object> findAll(int pageNumber, int noOfItems);
    PageDto searchCriteria(AuthorSpecs authorSpecs);
    void deleteAuthor(long id) throws GenericException;
    Optional<Author> findByEmail(String email);
}
