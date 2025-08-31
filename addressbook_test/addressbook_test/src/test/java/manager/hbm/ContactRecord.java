package manager.hbm;

import jakarta.persistence.*;

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

    public ContactRecord() {

    }

    public ContactRecord(int id, String firstname, String middlename, String lastname, String photo) {
    }

    @ManyToMany(mappedBy = "contacts")
    private List<GroupRecord> groups;
}
