package com.mobilise.task.controllers;

import com.mobilise.task.dtos.*;
import com.mobilise.task.models.Author;
import com.mobilise.task.services.interfaces.AuthorService;
import com.mobilise.task.utils.AppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.InvocationTargetException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid @NotBlank AuthorRequest authorRequest) {
        Author author = authorService.createAuthor(authorRequest);
        return new ResponseEntity<>(AuthorDto.toDto(author), HttpStatus.CREATED);
    }


    @GetMapping(value = "/search-author", produces = "application/json")
    public ResponseEntity<PageDto> searchCriteriaForAuthor(@RequestParam(value = "q", required = false) String query,
                          @RequestParam int page,
                          @RequestParam int size) {
        PageDto pageDto = authorService.searchCriteria(query, page, size);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<AuthorResponse> getAuthor(@RequestParam String authorId) {
        long id = AppendableReferenceUtils.getIdFrom(authorId);
        AuthorResponse author = authorService.findById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PutMapping(value = "/update", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateAuthor(@RequestParam String authorId, @RequestBody AuthorRequest authorRequest) throws InvocationTargetException, IllegalAccessException {
        long id = AppendableReferenceUtils.getIdFrom(authorId);
        authorService.updateAuthor(id, authorRequest);
    }


    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<PagedResponse> getAllAuthor(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo,
            @RequestParam(value = "noOfItems", required = false, defaultValue = "0") String numberOfItems){
        PagedResponse pagedResponse = authorService.findAll(Integer.parseInt(pageNo), Integer.parseInt(numberOfItems));
        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAuthor(@RequestParam String authorId) {
        long id = AppendableReferenceUtils.getIdFrom(authorId);
        authorService.deleteAuthor(id);
    }
}
