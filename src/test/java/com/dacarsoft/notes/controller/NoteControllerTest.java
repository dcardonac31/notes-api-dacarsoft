package com.dacarsoft.notes.controller;

import com.dacarsoft.notes.dto.NoteRequest;
import com.dacarsoft.notes.dto.NoteResponse;
import com.dacarsoft.notes.service.NoteService;
import com.dacarsoft.notes.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    // Test case for creating a note
    @Test
    public void testCreateNote() throws Exception {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setTitle("Test Title");
        noteRequest.setContent("Test Content");

        NoteResponse noteResponse = new NoteResponse();
        String noteIdMock = Util.generateUUID();
        noteResponse.setId(noteIdMock);
        noteResponse.setTitle(noteRequest.getTitle());
        noteResponse.setContent(noteRequest.getContent());

        Mockito.when(noteService.createNote(Mockito.any(NoteRequest.class))).thenReturn(noteResponse);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(("$.id")).value(noteIdMock))
                .andExpect(jsonPath("$.title").value(noteRequest.getTitle()));
    }

    // Test case for creating a note with invalid data
    @Test
    public void testCreateNoteWithInvalidData() throws Exception {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setTitle("");
        noteRequest.setContent("Test Content");

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test case for getting all notes
    @Test
    public void testGetAllNotes() throws Exception {
        NoteResponse noteResponse1 = new NoteResponse();
        noteResponse1.setId(Util.generateUUID());
        noteResponse1.setTitle("Test Title 1");
        noteResponse1.setContent("Test Content 1");

        NoteResponse noteResponse2 = new NoteResponse();
        noteResponse2.setId(Util.generateUUID());
        noteResponse2.setTitle("Test Title 2");
        noteResponse2.setContent("Test Content 2");

        List<NoteResponse> notes = List.of(noteResponse1, noteResponse2);

        Mockito.when(noteService.getAllNotes()).thenReturn(notes);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(noteResponse1.getId()))
                .andExpect(jsonPath("$[0].title").value(noteResponse1.getTitle()))
                .andExpect(jsonPath("$[1].id").value(noteResponse2.getId()))
                .andExpect(jsonPath("$[1].title").value(noteResponse2.getTitle()));
    }

    // Test case for getting a note by ID
    @Test
    public void testGetNoteById() throws Exception {
        String noteIdMock = Util.generateUUID();
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(noteIdMock);
        noteResponse.setTitle("Test Title");
        noteResponse.setContent("Test Content");

        Mockito.when(noteService.getNoteById(noteIdMock)).thenReturn(noteResponse);

        mockMvc.perform(get("/api/notes/" + noteIdMock))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteIdMock))
                .andExpect(jsonPath("$.title").value(noteResponse.getTitle()));
    }

    // Test case for updating a note
    @Test
    public void testUpdateNote() throws Exception {
        String noteIdMock = Util.generateUUID();
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setTitle("Updated Title");
        noteRequest.setContent("Updated Content");

        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(noteIdMock);
        noteResponse.setTitle(noteRequest.getTitle());
        noteResponse.setContent(noteRequest.getContent());

        Mockito.when(noteService.updateNote(Mockito.anyString(), Mockito.any(NoteRequest.class))).thenReturn(noteResponse);

        mockMvc.perform(put("/api/notes/" + noteIdMock)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteIdMock))
                .andExpect(jsonPath("$.title").value(noteRequest.getTitle()));
    }

    // Test case for deleting a note
    @Test
    public void testDeleteNote() throws Exception {
        String noteIdMock = Util.generateUUID();

        mockMvc.perform(delete("/api/notes/" + noteIdMock))
                .andExpect(status().isNoContent());

        Mockito.verify(noteService, Mockito.times(1)).deleteNote(noteIdMock);
    }
}
