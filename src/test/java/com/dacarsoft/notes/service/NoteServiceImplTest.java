package com.dacarsoft.notes.service;

import com.dacarsoft.notes.dto.NoteRequest;
import com.dacarsoft.notes.dto.NoteResponse;
import com.dacarsoft.notes.mapper.NoteMapper;
import com.dacarsoft.notes.model.Note;
import com.dacarsoft.notes.repository.NoteRepository;
import com.dacarsoft.notes.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteServiceImplTest {
    // Test cases for NoteServiceImpl will be added here
    // This class will contain unit tests for the methods in NoteServiceImpl
    // using JUnit and Mockito for mocking dependencies.

    // Mocking the NoteRepository to isolate the service layer from the database
    @Mock
    private NoteRepository noteRepository;

    // Injecting the mocked NoteRepository into the NoteServiceImpl
    @InjectMocks
    private NoteServiceImpl noteService;

    // This method will be executed before each test case
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNote() {
        NoteRequest request = new NoteRequest();
        request.setTitle("Test Title");
        request.setContent("Test Content");

        Note note = NoteMapper.toEntity(request);

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        NoteResponse response = noteService.createNote(request);

        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testGetNoteById_found() {
        Note note = new Note();
        String noteIdMock = Util.generateUUID();
        note.setId(noteIdMock);
        note.setTitle("Test Title");
        note.setContent("Test Content");

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        NoteResponse response = noteService.getNoteById(noteIdMock);

        assertNotNull(response);
        assertEquals(noteIdMock, response.getId());
        assertEquals(note.getTitle(), response.getTitle());
        assertEquals(note.getContent(), response.getContent());
        verify(noteRepository, times(1)).findById(noteIdMock);
    }

    @Test
    void testGetNoteById_notFound() {
        String noteIdMock = Util.generateUUID();

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            noteService.getNoteById(noteIdMock);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Note not found with id: " + noteIdMock, exception.getReason());
        verify(noteRepository, times(1)).findById(noteIdMock);
    }

    @Test
    void testGetAllNotes() {
        Note note1 = new Note();
        note1.setId(Util.generateUUID());
        note1.setTitle("Test Title 1");
        note1.setContent("Test Content 1");

        Note note2 = new Note();
        note2.setId(Util.generateUUID());
        note2.setTitle("Test Title 2");
        note2.setContent("Test Content 2");

        when(noteRepository.findAll()).thenReturn(List.of(note1, note2));

        List<NoteResponse> responses = noteService.getAllNotes();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals(note1.getTitle(), responses.get(0).getTitle());
        assertEquals(note2.getTitle(), responses.get(1).getTitle());
    }

    // Test for updateNote
//    public NoteResponse getNoteById(String id) {
//        Note note = noteRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
//        return NoteMapper.toResponse(note);
//    }
    @Test
    void testUpdateNote() {
        String notTitleMock = "Test Title";
        String noteContentMock = "Test Content";

        NoteRequest request = new NoteRequest(
                notTitleMock,
                noteContentMock
        );


        String noteIdMock = Util.generateUUID();
        Note note = new Note();
        note.setId(noteIdMock);
        note.setTitle("Old Title");
        note.setContent("Old Content");

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        NoteResponse response = noteService.updateNote(noteIdMock, request);

        assertNotNull(response);
        assertEquals(request.getTitle(), response.getTitle());
        assertEquals(request.getContent(), response.getContent());
        verify(noteRepository, times(1)).findById(noteIdMock);
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testDeleteNote() {
        String noteIdMock = Util.generateUUID();
        Note note = new Note();
        note.setId(noteIdMock);

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.of(note));

        noteService.deleteNote(noteIdMock);

        verify(noteRepository, times(1)).delete(any(Note.class));
    }

    @Test
    void testDeleteNote_notFound() {
        String noteIdMock = Util.generateUUID();

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            noteService.deleteNote(noteIdMock);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Note not found with id: " + noteIdMock, exception.getReason());
    }



}
