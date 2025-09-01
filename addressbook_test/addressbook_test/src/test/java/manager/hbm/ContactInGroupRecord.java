package manager.hbm;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "address_in_groups")
public class ContactInGroupRecord {

    @Id
    @ManyToOne
    @JoinColumn(name = "id")
    public ContactRecord contact;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    public GroupRecord group;

    @Column(name = "created")
    public LocalDateTime created;

    public ContactInGroupRecord() {
    }

    public ContactInGroupRecord(ContactRecord contact, GroupRecord group, LocalDateTime created) {
        this.contact = contact;
        this.group = group;
        this.created = created;
    }
}
