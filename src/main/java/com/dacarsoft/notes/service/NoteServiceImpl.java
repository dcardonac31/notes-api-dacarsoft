package com.dacarsoft.notes.service;

import com.dacarsoft.notes.dto.NoteRequest;
import com.dacarsoft.notes.dto.NoteResponse;
import com.dacarsoft.notes.mapper.NoteMapper;
import com.dacarsoft.notes.model.Note;
import com.dacarsoft.notes.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service interface for managing notes.
 * This interface defines the methods for creating, retrieving, updating, and deleting notes.
 */
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    /**
     * Constructor for NoteServiceImpl.
     * @param noteRepository the repository for note operations
     */
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public NoteResponse createNote(NoteRequest noteRequest) {
        Note note = NoteMapper.toEntity(noteRequest);
        Note savedNote = noteRepository.save(note);
        return NoteMapper.toResponse(savedNote);
    }

    @Override
    public NoteResponse getNoteById(String id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
        return NoteMapper.toResponse(note);
    }

    @Override
    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll()
                .stream()
                .map(NoteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponse updateNote(String id, NoteRequest noteRequest) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
        NoteMapper.updateEntity(note, noteRequest);
        Note updatedNote = noteRepository.save(note);
        return NoteMapper.toResponse(updatedNote);
    }

    @Override
    public void deleteNote(String id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
        noteRepository.delete(note);
    }
}
