package manager.hbm;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "addressbook")
public class ContactRecord {

    @Id
    @Column(name = "id")
    public int id;

    @Column(name = "firstname")
    public String firstname;

    @Column(name = "middlename")
    public String middlename;

    @Column(name = "lastname")
    public String lastname;

    @Column(name = "photo")
    public String photo;

    //public Date deprecated = new Date();
    public Date created = new Date();
    public Date modified = new Date();

    public String home = "";
    public String mobile = "";
    public String work = "";
    public String phone2 = "";
    public String company = "";
    public String title = "";
    public String fax = "";
    public String email = "";
    public String email2 = "";
    public String email3 = "";
    public String homepage = "";
    public String nickname = "";
    public String address = "";
    public String byear = "";
    public String ayear = "";

    @Column(name = "bday", columnDefinition = "TINYINT")
    public int bday = 0;

    public String bmonth = "-";

    @Column(name = "aday", columnDefinition = "TINYINT")
    public int aday = 0;

    public String amonth = "-";

    public ContactRecord() {

    }

    public ContactRecord(int id, String firstname, String middlename, String lastname, String photo) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.photo = photo;
    }

    @ManyToMany(mappedBy = "contacts")
    private List<GroupRecord> groups;
}
