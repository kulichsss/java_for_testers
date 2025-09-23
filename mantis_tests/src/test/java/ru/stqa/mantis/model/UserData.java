package ru.stqa.mantis.model;

public record UserData(String username, String password, String realname, String email) {
    public UserData() {
        this("","","","");
    }

    public UserData withUsername(String username) {
        return new UserData(username, this.password, this.realname, this.email);
    }
    public UserData withPassword(String password) {
        return new UserData(this.username, password, this.realname, this.email);
    }
    public UserData withRealname(String realname) {
        return new UserData(this.username, this.password, realname, this.email);
    }
    public UserData withEmail(String email) {
        return new UserData(this.username, this.password, this.realname, email);
    }

}
