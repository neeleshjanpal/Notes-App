package com.notesapp.dto;

import jakarta.validation.constraints.Size;

public class NoteRequest {
    
    public static class CreateNoteRequest {
        @Size(max = 500)
        private String title = "";
        
        private String content = "";
        
        // Constructors
        public CreateNoteRequest() {}
        
        public CreateNoteRequest(String title, String content) {
            this.title = title;
            this.content = content;
        }
        
        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
    
    public static class UpdateNoteRequest {
        @Size(max = 500)
        private String title;
        
        private String content;
        
        private Integer version;
        
        // Constructors
        public UpdateNoteRequest() {}
        
        public UpdateNoteRequest(String title, String content, Integer version) {
            this.title = title;
            this.content = content;
            this.version = version;
        }
        
        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        public Integer getVersion() { return version; }
        public void setVersion(Integer version) { this.version = version; }
    }
}