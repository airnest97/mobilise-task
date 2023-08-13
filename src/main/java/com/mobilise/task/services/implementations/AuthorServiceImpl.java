package com.mobilise.task.services.implementations;

import com.mobilise.task.dtos.*;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.models.Author;
import com.mobilise.task.models.Book;
import com.mobilise.task.repositories.AuthorRepository;
import com.mobilise.task.services.interfaces.AuthorService;
import com.mobilise.task.specifications.AuthorSpecs;
import com.mobilise.task.utils.BeanUtilHelper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.mobilise.task.utils.Constants.ALREADY_EXIST;
import static com.mobilise.task.utils.Constants.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    
    private final AuthorRepository authorRepository;
    public static final String AUTHOR_EVENT = "AUTHOR_SERVICE";
    public static Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);


    @Override
    public Author createAuthor(AuthorRequest authorRequest) throws GenericException {
        authorDoesNotExist(authorRequest.getEmail());
        Author author = new Author();
        BeanUtils.copyProperties(authorRequest, author);
        logger.info("Event={}, author={}, authorRequest={}", AUTHOR_EVENT, author, authorRequest);
        return authorRepository.save(author);
    }

    private void authorDoesNotExist(String email) throws GenericException {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
        if(optionalAuthor.isPresent()){
            throw new GenericException(ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
    }

    public void save(Author author){
        authorRepository.save(author);
    }

    public Optional<Author> findByEmail(String email){
        return authorRepository.findByEmail(email);
    }

    @Override
    public AuthorResponse findById(long id) throws GenericException {
        Author author = authorRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
        logger.info("Event={}, author={}", AUTHOR_EVENT, author);
        return AuthorResponse.fromModel(author);
    }

    @Override
    public void updateAuthor(long id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException, GenericException {
        Author author = this.findAuthor(id);
        BeanUtilHelper.copyPropertiesIgnoreNull(updateAuthorRequest, author);
        authorRepository.save(author);
    }

    @Override
    public Map<String, Object> findAll(int pageNumber, int noOfItems) {
        Pageable pageable = PageRequest.of(pageNumber, noOfItems, Sort.by("firstName"));
        Page<Author> page = authorRepository.findAll(pageable);
        Map<String, Object> pageResult = new HashMap<>();

        List<Author> authors = page.getContent();
        List<AuthorResponse> authorResponses = mapAuthorToAuthorResponse(authors);

        pageResult.put("totalNumberOfPages", page.getTotalPages());
        pageResult.put("totalNumberOfElementsInDatabase", page.getTotalElements());
        if (page.hasNext()) {
            pageResult.put("nextPage", page.nextPageable());
        }
        if (page.hasPrevious()) {
            pageResult.put("previousPage", page.previousPageable());
        }
        pageResult.put("author", authorResponses);
        pageResult.put("NumberOfElementsInPage", page.getNumberOfElements());
        pageResult.put("pageNumber", page.getNumber());
        pageResult.put("size", page.getSize());
        return pageResult;
    }

    private static List<AuthorResponse> mapAuthorToAuthorResponse(List<Author> authors) {
        return authors.stream().map(author -> {
           AuthorResponse authorResponse = new AuthorResponse();
            BeanUtils.copyProperties(author,authorResponse);
            List<BookResponse> bookResponses = mapBookToBookResponse(author);
            authorResponse.setBooks(bookResponses);
            return authorResponse;
        }).toList();
    }

    private static List<BookResponse> mapBookToBookResponse(Author author) {
        List<Book> books = author.getBooks();
        return books.stream().map(book -> {
           BookResponse bookResponse = new BookResponse();
            BeanUtils.copyProperties(book, bookResponse);
            return bookResponse;
        }).toList();
    }


    @Override
    public PageDto searchCriteria(AuthorSpecs authorSpecs) {
        Page<Author> authorPage = authorRepository.findAll(authorSpecs, authorSpecs.getPageable());
        return PageDto.build(authorPage, AuthorDto::toDto);
    }

    @Override
    public void deleteAuthor(long id) throws GenericException {
        Author author = this.findAuthor(id);

        if (author != null) {
            // Remove the author's association from books
            for (Book book : author.getBooks()) {
                book.getAuthors().remove(author);
            }
            // Clear the author's association from books
            author.getBooks().clear();
            authorRepository.delete(author);
        }
    }

    private Author findAuthor(long id) throws GenericException {
        return authorRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
