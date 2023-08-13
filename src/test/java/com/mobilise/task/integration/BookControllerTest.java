package com.mobilise.task.integration;

import com.mobilise.task.controllers.BookController;
import com.mobilise.task.dtos.BookDto;
import com.mobilise.task.dtos.PagedResponse;
import com.mobilise.task.enums.AgeRating;
import com.mobilise.task.enums.Genre;
import com.mobilise.task.enums.Language;
import com.mobilise.task.services.interfaces.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;
    private BookDto book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();

        book = new BookDto();
        book.setAgeRating(AgeRating.PG13);
        book.setUrl("amzon.hdskwjhg.com");
        book.setGenre(Genre.FICTION);
        book.setIsbn(1222222);
        book.setDescription("Just to test");
        book.setLanguage(Language.ENGLISH);
        book.setPrice(234);
        book.setTitle("fgdhjkj");
        book.setPublicationYear(2022);
        book.setDateUploaded(LocalDate.parse("2023-08-12"));
    }

    @Test
    public void createBookTest() throws Exception {


        when(bookService.createBook(any())).thenReturn(book);

        mockMvc.perform(multipart("/api/v1/book/create")
                        .file("file", new byte[0])
                        .param("bookRequest", "{}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getBookTest() throws Exception {
        when(bookService.findById(any(Long.class))).thenReturn(book);

        mockMvc.perform(get("/api/v1/book/get")
                        .param("bookId", "1L"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void getAllBooksTest() throws Exception {
        when(bookService.findAll(any(Integer.class), any(Integer.class))).thenReturn(new PagedResponse());

        // Perform the request and assert the response
        mockMvc.perform(get("/api/v1/book/all")
                        .param("pageNo", "0")
                        .param("noOfItems", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

}