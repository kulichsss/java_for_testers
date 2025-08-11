package model;

public record ContactData(String firstname, String middlename, String lastname, String nickname, String title, String company, String address, String email, String homePhone, String workPhone, String faxPhone, String mobile) {
    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "");
    }

    public ContactData withRequiredFields(String firstname, String middlename, String lastname, String address, String email, String mobile) {
        return new ContactData(firstname, middlename, lastname, this.nickname, this.title, this.company, address, email, this.homePhone, this.workPhone, this.faxPhone, mobile);
    }
}
