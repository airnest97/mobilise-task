package com.mobilise.task.service;

import com.mobilise.task.dtos.AuthorRequest;
import com.mobilise.task.dtos.AuthorResponse;
import com.mobilise.task.dtos.PagedResponse;
import com.mobilise.task.exception.GenericException;
import com.mobilise.task.models.Author;
import com.mobilise.task.repositories.AuthorRepository;
import com.mobilise.task.services.implementations.AuthorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class AuthorServiceTest {

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    private AuthorRequest authorRequest;
    private Author savedAuthor;

    @BeforeEach
    void setUp() throws GenericException {
        authorRequest = new AuthorRequest();
        authorRequest.setFirstName("Ernest");
        authorRequest.setLastName("James");
        authorRequest.setMiddleName("Jeremiah");
        authorRequest.setEmail("ernest@gmail.com");
        authorRequest.setBio("dummy bio");

        savedAuthor = new Author();
        savedAuthor.setId(1L);
        savedAuthor.setFirstName("Ernest");
        savedAuthor.setLastName("James");
        savedAuthor.setMiddleName("Jeremiah");
        savedAuthor.setEmail("ernest@gmail.com");
        savedAuthor.setBio("dummy bio");

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        authorService = new AuthorServiceImpl(authorRepository);
        savedAuthor = authorService.createAuthor(authorRequest);
    }

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    void createAuthorTest() {
        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);
        AuthorRequest authorRequest2 = new AuthorRequest();
        authorRequest2.setFirstName("Kayode");
        authorRequest2.setLastName("James");
        authorRequest2.setMiddleName("Jeremiah");
        authorRequest2.setEmail("ernest11@gmail.com");
        authorRequest2.setBio("dummy bio");
        authorService.createAuthor(authorRequest2);
        verify(authorRepository, times(2)).save(any(Author.class));
    }

    @Test
    void twoAuthorCannotExistWithSameEmailTest() {
        when(authorRepository.findByEmail(any(String.class))).thenReturn(Optional.ofNullable(savedAuthor));
        assertThrows(GenericException.class, () -> authorService.createAuthor(authorRequest));
        verify(authorRepository, times(2)).findByEmail(any(String.class));
    }

    @Test
    void findByIdTest() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(savedAuthor));
        AuthorResponse authorResponse = authorService.findById(1L);
        assertNotNull(authorResponse);
        assertEquals("Ernest", authorResponse.getFirstName());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteAuthorTest() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(savedAuthor));
        authorService.deleteAuthor(savedAuthor.getId());
        verify(authorRepository, times(1)).delete(savedAuthor);
    }

    @Test
    void updateAuthorTest() throws InvocationTargetException, IllegalAccessException {
        AuthorRequest updatedAuthor = new AuthorRequest();
        updatedAuthor.setFirstName("Ahmed");
        updatedAuthor.setLastName("James");
        updatedAuthor.setMiddleName("Jeremiah");
        updatedAuthor.setEmail("ernest11@gmail.com");
        updatedAuthor.setBio("new bio");
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(savedAuthor));
        authorService.updateAuthor(1L, updatedAuthor);
        verify(authorRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(2)).save(any(Author.class));
    }

    @Test
    void findAllTest() {
        Page<Author> authorPage = new PageImpl<>(Collections.singletonList(savedAuthor));
        when(authorRepository.findAll(any(Pageable.class))).thenReturn(authorPage);
        PagedResponse result = authorService.findAll(0, 1);
        assertEquals(1, result.getPagedResponse().get("totalNumberOfPages"));
        assertEquals(1, result.getPagedResponse().get("size"));
        verify(authorRepository, times(1)).findAll(any(Pageable.class));
    }
}

