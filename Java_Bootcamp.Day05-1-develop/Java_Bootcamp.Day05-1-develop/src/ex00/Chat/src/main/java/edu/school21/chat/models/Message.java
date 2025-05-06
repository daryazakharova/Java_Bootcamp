package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private final Long id;
    private User author; 
    private Chatroom room; 
    private String text; 
    private LocalDateTime createdAt; 

    // Constructor
    public Message(Long id, User author, Chatroom room, String text, LocalDateTime createdAt) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
         return id == message.getId() && author.equals(message.getAuthor()) && room.equals(message.getRoom())
                && text.equals(message.getText()) && createdAt.equals(message.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, createdAt);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author + 
                ", room=" + room + 
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}