package ru.stqa.mantis.model;

public record MailData(String from, String content) {

    public MailData() {
        this("", "");
    }

    public MailData withFrom(String from) {
        return new MailData(from, this.content);
    }

    public MailData withContent(String content) {
        return new MailData(this.from, content);
    }

}
