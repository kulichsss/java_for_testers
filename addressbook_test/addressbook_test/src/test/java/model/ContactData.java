package model;

public record ContactData(String id, String firstname, String middlename, String lastname, String photo) {
    public ContactData() {
        this("", "", "", "", "");
    }

    public ContactData withRequiredFields(String firstname, String middlename, String lastname) {
        return new ContactData(this.id, firstname, middlename, lastname, "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstname, this.middlename, this.lastname, this.photo);
    }

    public ContactData withName(String firstname) {
        return new ContactData(this.id, firstname, this.middlename, this.lastname, this.photo);
    }
    public ContactData withLastname(String lastname) {
        return new ContactData(this.id, this.firstname, this.middlename, lastname, this.photo);
    }
    public ContactData withMiddlename(String middlename) {
        return new ContactData(this.id, this.firstname, middlename, this.lastname, this.photo);
    }

    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, photo);
    }

}
