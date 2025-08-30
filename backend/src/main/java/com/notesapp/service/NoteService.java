package com.notesapp.service;

import com.notesapp.entity.Note;
import com.notesapp.entity.User;
import com.notesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    public List<Note> findAllByUserId(UUID userId) {
        return noteRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }
    
    public Optional<Note> findByIdAndUserId(UUID id, UUID userId) {
        return noteRepository.findByIdAndUserId(id, userId);
    }
    
    public Note createNote(String title, String content, User user) {
        Note note = new Note(title, content, user);
        return noteRepository.save(note);
    }
    
    public Note updateNote(UUID id, String title, String content, Integer expectedVersion, UUID userId) {
        Optional<Note> optionalNote = noteRepository.findByIdAndUserId(id, userId);
        
        if (optionalNote.isEmpty()) {
            throw new RuntimeException("Note not found");
        }
        
        Note note = optionalNote.get();
        
        // Optimistic locking check
        if (!note.getVersion().equals(expectedVersion)) {
            throw new OptimisticLockException("Note has been modified by another session");
        }
        
        if (title != null) {
            note.setTitle(title);
        }
        if (content != null) {
            note.setContent(content);
        }
        
        return noteRepository.save(note);
    }
    
    public void deleteNote(UUID id, UUID userId) {
        Optional<Note> note = noteRepository.findByIdAndUserId(id, userId);
        if (note.isPresent()) {
            noteRepository.delete(note.get());
        } else {
            throw new RuntimeException("Note not found");
        }
    }
    
    public long countUserNotes(UUID userId) {
        return noteRepository.countByUserId(userId);
    }
    
    public static class OptimisticLockException extends RuntimeException {
        public OptimisticLockException(String message) {
            super(message);
        }
    }
}