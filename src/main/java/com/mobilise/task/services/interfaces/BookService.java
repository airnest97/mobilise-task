package com.mobilise.task.services.interfaces;

import com.mobilise.task.dtos.BookDto;
import com.mobilise.task.dtos.BookRequest;
import com.mobilise.task.dtos.PageDto;
import com.mobilise.task.dtos.PagedResponse;
import com.mobilise.task.exception.GenericException;
import java.io.IOException;

public interface BookService {
    BookDto createBook(BookRequest bookRequest) throws IOException;
    BookDto findById(long id) throws GenericException;
    void updateBook(long id, BookRequest bookRequest) throws IOException;
    PagedResponse findAll(int pageNumber, int noOfItems);
    PageDto searchCriteria(String query, int page, int size);
    void deleteBook(long id);
}
