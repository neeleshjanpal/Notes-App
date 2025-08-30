import React from 'react';
import { Grid, Box, Typography } from '@mui/material';
import { Note } from '../../types';
import { NoteCard } from './NoteCard';

interface NotesGridProps {
  notes: Note[];
  loading: boolean;
  onEditNote: (note: Note) => void;
  onDeleteNote: (id: string) => void;
}

export const NotesGrid: React.FC<NotesGridProps> = ({ 
  notes, 
  loading, 
  onEditNote, 
  onDeleteNote 
}) => {
  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Loading notes...</Typography>
      </Box>
    );
  }

  if (notes.length === 0) {
    return (
      <Box sx={{ textAlign: 'center', mt: 8 }}>
        <Typography variant="h6" color="text.secondary" gutterBottom>
          No notes yet
        </Typography>
        <Typography variant="body2" color="text.secondary">
          Create your first note to get started!
        </Typography>
      </Box>
    );
  }

  return (
    <Grid container spacing={3}>
      {notes.map((note) => (
        <Grid item xs={12} sm={6} md={4} key={note.id}>
          <NoteCard 
            note={note} 
            onEdit={onEditNote} 
            onDelete={onDeleteNote} 
          />
        </Grid>
      ))}
    </Grid>
  );
};