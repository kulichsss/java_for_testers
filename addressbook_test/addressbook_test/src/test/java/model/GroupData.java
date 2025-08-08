package model;

public record GroupData(String name, String header, String footer) {

    public GroupData() {
        this("", "", "");
    }

    public GroupData withName(String name) {
        return new GroupData(name, this.header, this.footer);
    }
}