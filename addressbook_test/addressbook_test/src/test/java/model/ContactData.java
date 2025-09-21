package model;

public record ContactData(String id, String firstname, String middlename, String lastname, String photo, String home,
                          String mobile, String work, String fax, String address, String email, String email2,
                          String email3) {
    public ContactData() {
        this("", "", "", "", "", "", "", "", "", "", "", "", "");
    }

    public ContactData withRequiredFields(String firstname, String middlename, String lastname) {
        return new ContactData(this.id, firstname, middlename, lastname, "", "", "", "", "", "", "", "", "");
    }

    public ContactData withId(String id) {
        return new ContactData(id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }

    public ContactData withName(String firstname) {
        return new ContactData(this.id, firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }
    public ContactData withLastname(String lastname) {
        return new ContactData(this.id, this.firstname, this.middlename, lastname, this.photo, this.home, this.mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }
    public ContactData withMiddlename(String middlename) {
        return new ContactData(this.id, this.firstname, middlename, this.lastname, this.photo, this.home, this.mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }
    public ContactData withPhoto(String photo) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, photo, this.home, this.mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }

    public ContactData withHome(String home) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, home, this.mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }

    public ContactData withMobile(String mobile) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, mobile, this.work, this.fax, this.address, this.email, this.email2, this.email3);
    }

    public ContactData withWork(String work) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, work, this.fax, this.address, this.email, this.email2, this.email3);
    }

    public ContactData withFax(String fax) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, fax, this.address, this.email, this.email2, this.email3);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, this.fax, address, this.email, this.email2, this.email3);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, fax, this.address, email, this.email2, this.email3);
    }

    public ContactData withEmail2(String email2) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, fax, this.address, this.email, email2, this.email3);
    }

    public ContactData withEmail3(String email3) {
        return new ContactData(this.id, this.firstname, this.middlename, this.lastname, this.photo, this.home, this.mobile, this.work, fax, this.address, this.email, this.email2, email3);
    }

}
