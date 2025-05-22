package com.dacarsoft.notes.mapper;

import com.dacarsoft.notes.dto.NoteRequest;
import com.dacarsoft.notes.dto.NoteResponse;
import com.dacarsoft.notes.model.Note;

import static com.dacarsoft.notes.util.Util.generateUUID;

/*
 * NoteMapper is a utility class that provides methods to convert between NoteRequest, NoteResponse, and Note entities.
 * It is used to map data between the DTOs and the entity model.
 */
public class NoteMapper {
    // Convert NoteRequest to Note entity
    public static Note toEntity(NoteRequest noteRequest) {
        Note note = new Note();
        String noteId = generateUUID();
        note.setId(noteId);
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        return note;
    }

    // Convert Note entity to NoteResponse
    public static NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

    // Convert NoteResponse to Note entity
    public static void updateEntity(Note note, NoteRequest noteRequest) {
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
    }
}
