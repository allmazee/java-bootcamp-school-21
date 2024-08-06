package edu.school21.chat.models;

import java.util.Calendar;
import java.util.Objects;

public class Message {
    private long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private Calendar dateTime;

    public Message(long id, User author, Chatroom chatroom, String text, Calendar dateTime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.dateTime = dateTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id && Objects.equals(author, message.author) &&
                Objects.equals(chatroom, message.chatroom) &&
                Objects.equals(text, message.text) &&
                Objects.equals(dateTime, message.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, text, dateTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", room='" + chatroom + '\'' +
                ", text=" + text +
                ", date/time=" + dateTime +
                '}';
    }
}
