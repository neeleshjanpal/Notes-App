import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Box,
  Typography,
  Alert,
  CircularProgress,
} from '@mui/material';
import { Save, Close } from '@mui/icons-material';
import { Note, CreateNoteRequest, UpdateNoteRequest } from '../../types';
import { apiService } from '../../services/api';

interface NoteEditorProps {
  open: boolean;
  note: Note | null;
  onClose: () => void;
  onSave: () => void;
}

export const NoteEditor: React.FC<NoteEditorProps> = ({ open, note, onClose, onSave }) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (note) {
      setTitle(note.title);
      setContent(note.content);
    } else {
      setTitle('');
      setContent('');
    }
    setError('');
  }, [note, open]);

  const handleSave = async () => {
    setError('');
    setLoading(true);

    try {
      if (note) {
        const updateData: UpdateNoteRequest = {
          title,
          content,
          version: note.version,
        };
        await apiService.updateNote(note.id, updateData);
      } else {
        const createData: CreateNoteRequest = {
          title,
          content,
        };
        await apiService.createNote(createData);
      }
      onSave();
      onClose();
    } catch (err) {
      if (err instanceof Error) {
        setError(err.message);
      } else {
        setError('An error occurred while saving the note');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    setError('');
    onClose();
  };

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="md" fullWidth>
      <DialogTitle sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <Typography variant="h6">
          {note ? 'Edit Note' : 'Create New Note'}
        </Typography>
        {note && (
          <Typography variant="caption" color="text.secondary">
            Version {note.version}
          </Typography>
        )}
      </DialogTitle>
      
      <DialogContent>
        {error && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}
        
        <TextField
          fullWidth
          label="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          margin="normal"
          variant="outlined"
          autoFocus
        />
        
        <TextField
          fullWidth
          label="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          margin="normal"
          variant="outlined"
          multiline
          rows={12}
          placeholder="Start writing your note..."
        />
      </DialogContent>
      
      <DialogActions sx={{ p: 3, gap: 1 }}>
        <Button 
          onClick={handleClose} 
          startIcon={<Close />}
          disabled={loading}
        >
          Cancel
        </Button>
        <Button 
          onClick={handleSave} 
          variant="contained" 
          startIcon={loading ? <CircularProgress size={16} /> : <Save />}
          disabled={loading}
        >
          {loading ? 'Saving...' : 'Save Note'}
        </Button>
      </DialogActions>
    </Dialog>
  );
};