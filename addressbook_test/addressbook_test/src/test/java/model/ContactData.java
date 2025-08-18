package model;

public record ContactData(String id, String firstname, String middlename, String lastname
) {
    public ContactData() {
        this("", "", "", "");
    }

    public ContactData withRequiredFields(String firstname, String middlename, String lastname, String address, String email, String mobile) {
        return new ContactData(this.id, firstname, middlename, lastname);
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstname, this.middlename, this.lastname);
    }

    public ContactData withName(String firstname) {
        return new ContactData(this.id, firstname, this.middlename, this.lastname);
    }
    public ContactData withLastname(String lastname) {
        return new ContactData(this.id, this.firstname, this.middlename, lastname);
    }
    public ContactData withMiddlename(String middlename) {
        return new ContactData(this.id, this.firstname, middlename, this.lastname);
    }

}
