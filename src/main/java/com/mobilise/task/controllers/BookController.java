package com.mobilise.task.controllers;

import com.mobilise.task.annotation.ValidateMultipart;
import com.mobilise.task.dtos.BookDto;
import com.mobilise.task.dtos.BookRequest;
import com.mobilise.task.dtos.PageDto;
import com.mobilise.task.dtos.PagedResponse;
import com.mobilise.task.services.interfaces.BookService;
import com.mobilise.task.utils.AppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;


    /**
     * max size of each cv is 50mb
     */

    @PostMapping(value = "/create", produces = "application/json")
    @ValidateMultipart(extensions = "pdf,docx,doc", maxSize = 52428800)
    public ResponseEntity<BookDto> createBook(@ModelAttribute BookRequest bookRequest) throws IOException {
        BookDto book = bookService.createBook(bookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @GetMapping(value = "/search-book", produces = "application/json")
    public ResponseEntity<PageDto> searchCriteriaForBook(@RequestParam(value = "q") String query,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        PageDto pageDto = bookService.searchCriteria(query, page, size);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<BookDto> getBook(@RequestParam String bookId) {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        BookDto book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestParam String bookId, @ModelAttribute BookRequest bookRequest) throws IOException {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        bookService.updateBook(id, bookRequest);
    }


    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<PagedResponse> getAllBooks(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo,
            @RequestParam(value = "noOfItems", required = false, defaultValue = "1") String numberOfItems){
        PagedResponse pagedResponse = bookService.findAll(Integer.parseInt(pageNo), Integer.parseInt(numberOfItems));
        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBook(@RequestParam String bookId) {
        long id = AppendableReferenceUtils.getIdFrom(bookId);
        bookService.deleteBook(id);
    }

}
