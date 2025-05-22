package com.dacarsoft.notes.repository;

import com.dacarsoft.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Note entities.
 * This interface extends JpaRepository to provide CRUD operations for Note entities.
 */
public interface NoteRepository extends JpaRepository<Note, String> {
    // Additional custom query methods can be defined here if needed
    // For example, you can add methods to find notes by title, content, etc.
}
