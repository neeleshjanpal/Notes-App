import React from 'react';
import {
  Card,
  CardContent,
  Typography,
  Box,
  IconButton,
  Chip,
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';
import { format } from 'date-fns';
import { Note } from '../../types';

interface NoteCardProps {
  note: Note;
  onEdit: (note: Note) => void;
  onDelete: (id: string) => void;
}

export const NoteCard: React.FC<NoteCardProps> = ({ note, onEdit, onDelete }) => {
  const handleEdit = () => onEdit(note);
  const handleDelete = () => onDelete(note.id);

  return (
    <Card sx={{ mb: 2, cursor: 'pointer' }} onClick={handleEdit}>
      <CardContent>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 1 }}>
          <Typography variant="h6" component="h3" sx={{ flexGrow: 1, mr: 1 }}>
            {note.title || 'Untitled Note'}
          </Typography>
          <Box sx={{ display: 'flex', gap: 0.5 }}>
            <IconButton 
              size="small" 
              onClick={(e) => {
                e.stopPropagation();
                handleEdit();
              }}
              sx={{ '&:hover': { color: 'primary.main' } }}
            >
              <Edit fontSize="small" />
            </IconButton>
            <IconButton 
              size="small" 
              onClick={(e) => {
                e.stopPropagation();
                handleDelete();
              }}
              sx={{ '&:hover': { color: 'error.main' } }}
            >
              <Delete fontSize="small" />
            </IconButton>
          </Box>
        </Box>
        
        <Typography 
          variant="body2" 
          color="text.secondary" 
          sx={{ 
            mb: 2,
            display: '-webkit-box',
            WebkitLineClamp: 3,
            WebkitBoxOrient: 'vertical',
            overflow: 'hidden',
          }}
        >
          {note.content || 'No content'}
        </Typography>

        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <Typography variant="caption" color="text.secondary">
            {format(new Date(note.updatedAt), 'MMM d, yyyy • h:mm a')}
          </Typography>
          <Chip 
            label={`v${note.version}`} 
            size="small" 
            variant="outlined" 
            sx={{ fontSize: '0.7rem' }}
          />
        </Box>
      </CardContent>
    </Card>
  );
};