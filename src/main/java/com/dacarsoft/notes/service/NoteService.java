package com.dacarsoft.notes.service;

import com.dacarsoft.notes.dto.NoteRequest;
import com.dacarsoft.notes.dto.NoteResponse;

import java.util.List;

/**
 * Service interface for managing notes.
 * This interface defines the methods for creating, retrieving, updating, and deleting notes.
 */
public interface NoteService {
    // Create a new note
    NoteResponse createNote(NoteRequest noteRequest);

    // Get a note by ID
    NoteResponse getNoteById(String id);

    // Get all notes
    List<NoteResponse> getAllNotes();

    // Update an existing note
    NoteResponse updateNote(String id, NoteRequest noteRequest);

    // Delete a note by ID
    void deleteNote(String id);

}
