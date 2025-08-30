package com.notesapp.controller;

import com.notesapp.dto.NoteRequest;
import com.notesapp.dto.NoteResponse;
import com.notesapp.entity.Note;
import com.notesapp.entity.User;
import com.notesapp.service.NoteService;
import com.notesapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    
    @Autowired
    private NoteService noteService;
    
    @Autowired
    private UserService userService;
    
    private UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<User> user = userService.findByEmail(email);
        return user.map(User::getId).orElse(null);
    }
    
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        List<Note> notes = noteService.findAllByUserId(userId);
        List<NoteResponse> response = notes.stream()
                .map(NoteResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNote(@PathVariable UUID id) {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<Note> note = noteService.findByIdAndUserId(id, userId);
        return note.map(n -> ResponseEntity.ok(new NoteResponse(n)))
                  .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteRequest.CreateNoteRequest request) {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Note note = noteService.createNote(request.getTitle(), request.getContent(), userOpt.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(new NoteResponse(note));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable UUID id, 
                                       @Valid @RequestBody NoteRequest.UpdateNoteRequest request) {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            Note updatedNote = noteService.updateNote(
                id, 
                request.getTitle(), 
                request.getContent(), 
                request.getVersion(), 
                userId
            );
            return ResponseEntity.ok(new NoteResponse(updatedNote));
        } catch (NoteService.OptimisticLockException e) {
            // Return current version for conflict resolution
            Optional<Note> currentNote = noteService.findByIdAndUserId(id, userId);
            if (currentNote.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new NoteResponse(currentNote.get()));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Note has been modified by another session");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            noteService.deleteNote(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}