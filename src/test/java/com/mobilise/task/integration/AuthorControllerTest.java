package com.mobilise.task.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilise.task.controllers.AuthorController;
import com.mobilise.task.dtos.AuthorRequest;
import com.mobilise.task.dtos.AuthorResponse;
import com.mobilise.task.dtos.PagedResponse;
import com.mobilise.task.models.Author;
import com.mobilise.task.services.interfaces.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void testCreateAuthor() throws Exception {
        AuthorRequest authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Ernest");
        authorRequest.setLastName("James");
        authorRequest.setEmail("ernest@gmail.com");
        authorRequest.setBio("dummy bio");

        Author createdAuthor = new Author();
        createdAuthor.setId(1L);
        createdAuthor.setFirstName("Ernest");
        createdAuthor.setLastName("James");
        createdAuthor.setEmail("ernest@gmail.com");
        createdAuthor.setBio("dummy bio");

        when(authorService.createAuthor(any(AuthorRequest.class))).thenReturn(createdAuthor);

        mockMvc.perform(post("/api/v1/author/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Ernest"))
                .andExpect(jsonPath("$.lastName").value("James"))
                .andExpect(jsonPath("$.email").value("ernest@gmail.com"))
                .andExpect(jsonPath("$.bio").value("dummy bio"));
    }

    @Test
    public void getAuthorTest() throws Exception {
        when(authorService.findById(any(Long.class))).thenReturn(new AuthorResponse());

        mockMvc.perform(get("/api/v1/author/get")
                        .param("authorId", "1L"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }


    @Test
    public void getAllAuthorTest() throws Exception {
        when(authorService.findAll(any(Integer.class), any(Integer.class))).thenReturn(new PagedResponse());

        mockMvc.perform(get("/api/v1/author/all")
                        .param("pageNo", "0")
                        .param("noOfItems", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testRemoveAuthor() throws Exception {
        long authorId = 1L;

        mockMvc.perform(delete("/api/v1/author/delete")
                        .param("authorId", String.valueOf(authorId)))
                .andExpect(status().isNoContent());
    }
}
