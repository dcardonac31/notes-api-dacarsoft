package com.dacarsoft.notes.controller;

import com.dacarsoft.notes.dto.NoteRequest;
import com.dacarsoft.notes.dto.NoteResponse;
import com.dacarsoft.notes.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = noteService.createNote(noteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteResponse);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable UUID id) {
        NoteResponse noteResponse = noteService.getNoteById(id.toString());
        return ResponseEntity.ok(noteResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable String id,
            @Valid @RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = noteService.updateNote(id, noteRequest);
        return ResponseEntity.ok(noteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        noteService.deleteNote(id.toString());
        return ResponseEntity.noContent().build();
    }

}
