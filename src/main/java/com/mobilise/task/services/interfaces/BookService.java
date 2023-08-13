package com.mobilise.task.services.interfaces;

import com.mobilise.task.dtos.BookDto;
import com.mobilise.task.dtos.BookRequest;
import com.mobilise.task.dtos.PageDto;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.specifications.BookSpecs;

import java.io.IOException;
import java.util.Map;

public interface BookService {
    BookDto createBook(BookRequest bookRequest) throws IOException, GenericException;
    BookDto findById(long id) throws GenericException;
    void updateBook(long id, BookRequest bookRequest) throws GenericException, IOException;
    Map<String, Object> findAll(int pageNumber, int noOfItems);
    PageDto searchCriteria(BookSpecs bookSpecs);
    void deleteBook(long id) throws GenericException;
}
