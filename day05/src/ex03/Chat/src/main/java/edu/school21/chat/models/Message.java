package edu.school21.chat.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private Timestamp dateTime;

    public Message(long id, User author, Chatroom chatroom, String text, Timestamp dateTime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.dateTime = dateTime;
        formatDateTime();
//        LocalDateTime localDateTime = dateTime.toLocalDateTime();
//        DateTimeFormatter formatter = DateTimeFormatter.
//                ofPattern("yyyy-MM-dd HH:mm:ss");
//        this.dateTime = Timestamp.valueOf(localDateTime.format(formatter));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
        formatDateTime();
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
        return "Message : {" +
                "\n\tid=" + id +
                ",\n\tauthor=" + author +
                ",\n\troom=" + chatroom +
                ",\n\ttext=\"" + text + "\"" +
                ",\n\tdateTime=" + dateTime +
                "\n}";
    }

    private void formatDateTime() {
        LocalDateTime localDateTime = dateTime.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.
                ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTime = Timestamp.valueOf(localDateTime.format(formatter));
    }
}
