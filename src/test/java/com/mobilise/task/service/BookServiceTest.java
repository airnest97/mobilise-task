package com.mobilise.task.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilise.task.dtos.*;
import com.mobilise.task.enums.AgeRating;
import com.mobilise.task.enums.Genre;
import com.mobilise.task.enums.Language;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.models.Author;
import com.mobilise.task.models.Book;
import com.mobilise.task.repositories.BookRepository;
import com.mobilise.task.services.implementations.BookServiceImpl;
import com.mobilise.task.services.interfaces.AuthorService;
import com.mobilise.task.services.interfaces.BookService;
import com.mobilise.task.services.storage.IStorageService;
import com.mobilise.task.services.storage.UploadObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    @Mock
    private  IStorageService iStorageService;

    private String bucketName = "bookBucket";
    @Mock
    private ObjectMapper objectMapper;
    private BookService bookService;
    private BookRequest bookRequest;
    private Book book;
    private Author author;
    private List<Author> authors;

    @BeforeEach
    void setUp() throws IOException {
        bookService  = new BookServiceImpl(bookRepository,authorService,iStorageService,bucketName,objectMapper);
        bookRequest = new BookRequest();
        bookRequest.setAgeRating(AgeRating.PG13);
        bookRequest.setFile(new MockMultipartFile("test file",new ByteArrayInputStream(new byte[4])));
        bookRequest.setGenre(Genre.FICTION);
        bookRequest.setIsbn(1222222);
        bookRequest.setDescription("Just to test");
        bookRequest.setLanguage(Language.ENGLISH);
        bookRequest.setPrice(234);
        bookRequest.setTitle("fgdhjkj");
        bookRequest.setAuthorList("[{\"firstName\": \"Ernest\", \"middleName\": \"john\", \"lastName\": \"Doe\", \"email\": \"ernestehigiator@yahoo.com\", \"bio\": \"dummy bio\"}]");
        bookRequest.setPublicationYear(2022);
        bookRequest.setDateOfUpload("2023-08-12");
        authors = new ArrayList<>();
        author = new Author();
        author.setEmail("ernestehigiator@yahoo.com");
        author.setFirstName("Ernest");
        author.setMiddleName("john");
        author.setLastName("Doe");
        author.setBio("dummy bio");
        authors.add(author);


        book = new Book();
        book.setAgeRating(AgeRating.PG13);
        book.setUrl("amzon.hdskwjhg.com");
        book.setFileName("PENtyudijhb");
        book.setGenre(Genre.FICTION);
        book.setIsbn(1222222);
        book.setDescription("Just to test");
        book.setLanguage(Language.ENGLISH);
        book.setPrice(234);
        book.setTitle("fgdhjkj");
        book.setAuthors(authors);
        book.setPublicationYear(2022);
        book.setDateUploaded(LocalDate.parse("2023-08-12"));

    }

    @Test
    public void testThatBookCanBeCreated() throws IOException, GenericException {
        when(bookRepository.findBookByIsbn(any(Integer.class))).thenReturn(Optional.empty());
        AuthorDto authorDto = new AuthorDto();
        authorDto.setEmail("ernestehigiator@yahoo.com");
        authorDto.setFirstName("Ernest");
        authorDto.setMiddleName("john");
        authorDto.setLastName("Doe");
        authorDto.setBio("dummy bio");
        AmazonResponse amazonResponse = AmazonResponse.builder().url("amzon.hdskwjhg.com").fileName("PENtyudijhb").build();
        List<AuthorDto> authorDtos = new ArrayList<>();
        authorDtos.add(authorDto);
        when(objectMapper.readValue(any(String.class), any(TypeReference.class)))
                .thenReturn(authorDtos);
        when(authorService.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(authorService.createAuthor(any(AuthorRequest.class))).thenReturn(author);
        when(iStorageService.uploadToBucket(any(InputStream.class),any(UploadObject.class))).thenReturn(amazonResponse);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDto bookDto = bookService.createBook(bookRequest);
        assertThat(bookDto.getGenre()).isEqualTo(Genre.FICTION);
        assertThat(bookDto.getLanguage()).isEqualTo(Language.ENGLISH);
    }

    @Test
    void findByIdTest() throws GenericException {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        BookDto bookDto = bookService.findById(1L);
        assertThat(bookDto).isNotEqualTo(null);
    }

    @Test
    void findAllTest() {
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));
        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        Map<String, Object> result = bookService.findAll(0, 1);
        assertEquals(1, result.get("totalNumberOfPages"));
        assertEquals(1, result.get("size"));
        verify(bookRepository, times(1)).findAll(any(Pageable.class));
    }

}
