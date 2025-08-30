package com.notesapp.repository;

import com.notesapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    
    @Query("SELECT n FROM Note n WHERE n.user.id = :userId ORDER BY n.updatedAt DESC")
    List<Note> findByUserIdOrderByUpdatedAtDesc(@Param("userId") UUID userId);
    
    @Query("SELECT n FROM Note n WHERE n.id = :id AND n.user.id = :userId")
    Optional<Note> findByIdAndUserId(@Param("id") UUID id, @Param("userId") UUID userId);
    
    @Query("SELECT COUNT(n) FROM Note n WHERE n.user.id = :userId")
    long countByUserId(@Param("userId") UUID userId);
}