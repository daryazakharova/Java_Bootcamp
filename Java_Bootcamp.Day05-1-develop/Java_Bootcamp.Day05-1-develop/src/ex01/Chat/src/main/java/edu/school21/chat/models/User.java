package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private final long id;
    private final String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> chatRooms;

    // Constructor
    public User(long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> chatRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.chatRooms = chatRooms;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Chatroom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<Chatroom> chatRooms) {
        this.chatRooms = chatRooms;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User ) o;
        return id == user.getId() && login.equals(user.getLogin()) && password.equals(user.getPassword())
                && createdRooms.equals(user.getCreatedRooms()) && chatRooms.equals(user.getChatRooms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdRooms, chatRooms);
    }

    @Override
    public String toString() {
        return  "{id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createdRooms +
                ", chatRooms=" + chatRooms +
                '}';
    }
}