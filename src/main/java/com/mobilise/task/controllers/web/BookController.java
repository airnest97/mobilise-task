package com.mobilise.task.controllers.web;

import com.mobilise.task.annotation.ValidateMultipart;
import com.mobilise.task.dtos.BookDto;
import com.mobilise.task.dtos.BookRequest;
import com.mobilise.task.dtos.PageDto;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.services.interfaces.BookService;
import com.mobilise.task.specifications.BookSpecs;
import com.mobilise.task.utils.IAppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;
    private static final String BOOK_CACHE = "bookCache";


    /**
     * max size of each cv is 50mb
     */
    @PostMapping(value = "/create", produces = "application/json")
    @ValidateMultipart(extensions = "pdf,docx,doc", maxSize = 52428800)
    public ResponseEntity<?> createBook(@ModelAttribute BookRequest bookRequest) throws IOException, GenericException {
        BookDto book = bookService.createBook(bookRequest);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }


    @GetMapping(value = "/searchBook", produces = "application/json")
    @Cacheable(value = BOOK_CACHE)
    public ResponseEntity<PageDto> searchCriteriaForBook(@RequestParam String query,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        BookSpecs bookSpecs = new BookSpecs(query);
        bookSpecs.setPage(page);
        bookSpecs.setSize(size);
        PageDto pageDto = bookService.searchCriteria(bookSpecs);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get", produces = "application/json")
    @Cacheable(value = BOOK_CACHE)
    public ResponseEntity<?> getBook(@RequestParam String bookId) throws GenericException {
        long id = IAppendableReferenceUtils.getIdFrom(bookId);
        BookDto book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PatchMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> updateBook(@RequestParam String bookId, @ModelAttribute BookRequest bookRequest) throws GenericException, IOException {
        long id = IAppendableReferenceUtils.getIdFrom(bookId);
        bookService.updateBook(id, bookRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/all", produces = "application/json")
    @Cacheable(value = BOOK_CACHE)
    public ResponseEntity<?> getAllBooks(
            @RequestParam(value = "pageNo", required = false) @DefaultValue({"0"}) @NotNull String pageNo,
            @RequestParam(value = "noOfItems", required = false) @DefaultValue({"10"}) @NotNull String numberOfItems){
        Map<String, Object> pageResult = bookService.findAll(Integer.parseInt(pageNo), Integer.parseInt(numberOfItems));
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<?> removeBook(@RequestParam String bookId) throws GenericException {
        long id = IAppendableReferenceUtils.getIdFrom(bookId);
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
