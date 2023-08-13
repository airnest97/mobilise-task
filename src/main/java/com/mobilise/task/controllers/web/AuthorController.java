package com.mobilise.task.controllers.web;

import com.mobilise.task.dtos.*;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.models.Author;
import com.mobilise.task.services.interfaces.AuthorService;
import com.mobilise.task.specifications.AuthorSpecs;
import com.mobilise.task.utils.IAppendableReferenceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/author")
@CrossOrigin(origins = "*")
public class AuthorController {

    private final AuthorService authorService;
    private static final String AUTHOR_CACHE = "authorCache";

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> createAuthor(@RequestBody @Valid @NotBlank AuthorRequest authorRequest) throws GenericException {
        Author author = authorService.createAuthor(authorRequest);
        return new ResponseEntity<>(AuthorDto.toDto(author), HttpStatus.CREATED);
    }


    @GetMapping(value = "/searchAuthor", produces = "application/json")
    @Cacheable(value = AUTHOR_CACHE)
    public ResponseEntity<PageDto> searchCriteriaForAuthor(@RequestParam(value = "q", required = false) String query,
                          @RequestParam int page,
                          @RequestParam int size) {
        AuthorSpecs authorSpecs = new AuthorSpecs(query);
        authorSpecs.setPage(page);
        authorSpecs.setSize(size);
        PageDto pageDto = authorService.searchCriteria(authorSpecs);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get", produces = "application/json")
    @Cacheable(value = AUTHOR_CACHE)
    public ResponseEntity<?> getAuthor(@RequestParam String authorId) throws GenericException {
        long id = IAppendableReferenceUtils.getIdFrom(authorId);
        AuthorResponse author = authorService.findById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PatchMapping(value = "/update", produces = "application/json")
    public ResponseEntity<?> updateAuthor(@RequestParam String authorId, @RequestBody AuthorRequest authorRequest) throws GenericException, InvocationTargetException, IllegalAccessException {
        long id = IAppendableReferenceUtils.getIdFrom(authorId);
        authorService.updateAuthor(id, authorRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/all", produces = "application/json")
    @Cacheable(value = AUTHOR_CACHE)
    public ResponseEntity<?> getAllAuthor(
            @RequestParam(value = "pageNo", required = false, defaultValue = "0") String pageNo,
            @RequestParam(value = "noOfItems", required = false, defaultValue = "0") String numberOfItems){
        Map<String, Object> pageResult = authorService.findAll(Integer.parseInt(pageNo), Integer.parseInt(numberOfItems));
        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setPagedResponse(pageResult);
        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);
    }


    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<?> removeAuthor(@RequestParam String authorId) throws GenericException {
        long id = IAppendableReferenceUtils.getIdFrom(authorId);
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
