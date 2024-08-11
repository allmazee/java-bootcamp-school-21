package edu.school21.sockets.models;

import java.time.LocalDateTime;

public class Message {
    private long id;
    private User author;
    private String text;
    private LocalDateTime dateTime;

    public Message(User author, String text, LocalDateTime dateTime) {
        this.author = author;
        this.text = text;
        this.dateTime = dateTime;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Message : {\n")
                .append("\tid=")
                .append(id)
                .append(",\n")
                .append("\tauthor={")
                .append(author.toString())
                .append("},\n")
                .append("\ttext=")
                .append("\"")
                .append(text)
                .append("\"")
                .append(",\n")
                .append("\tdateTime=")
                .append(dateTime.toString())
                .append(",\n}");
        return result.toString();
    }

}
