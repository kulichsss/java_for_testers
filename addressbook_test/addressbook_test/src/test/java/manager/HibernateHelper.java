package manager;

import jakarta.persistence.TypedQuery;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HibernateHelper extends HelperBase {
    private final SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        this.sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        try {
            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException("Ошибка при создании SessionFactory", e);
        }
    }

    public List<GroupData> convertList(List<GroupRecord> records) {
        List<GroupData> result = new ArrayList<>();
        for (var record: records) {
            result.add(new GroupData("" + record.id, record.name, record.header, record.footer));
        }
        return result;
    }

    public GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    public GroupRecord convert(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
    }

    public List<GroupData> getGroupsList() {
        Session session = sessionFactory.openSession();
        try {
            TypedQuery<GroupRecord> query = session.createQuery("FROM GroupRecord", GroupRecord.class);
            return convertList(query.getResultList());
        } finally {
            session.close();
        }
    }

    public void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }

    public long getCountGroups() {
        Session session = sessionFactory.openSession();
        try {
            TypedQuery<Long> query = session.createQuery("SELECT COUNT (*) FROM GroupRecord", Long.class);
            return query.getSingleResult();
        } finally {
            session.close();
        }
    }


    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }

    public List<ContactData> convertContactList(List<ContactRecord> records) {
        List<ContactData> result = new ArrayList<>();
        for (var record: records) {
            result.add(new ContactData("" + record.id, record.firstname, record.middlename, record.lastname, record.photo));
        }
        return result;
    }

    public ContactData convert(ContactRecord record) {
        return new ContactData("" + record.id, record.firstname, record.middlename, record.lastname, record.photo);
    }

    public ContactRecord convert(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(Integer.parseInt(id), data.firstname(), data.middlename(), data.lastname(), data.photo());
    }

    public List<ContactData> getContactsList() {
        Session session = sessionFactory.openSession();
        try {
            TypedQuery<ContactRecord> query = session.createQuery("FROM ContactRecord", ContactRecord.class);
            return convertContactList(query.getResultList());
        } finally {
            session.close();
        }
    }


    public List<ContactData> getContactsListInGroup(GroupData group) {
            Session session = sessionFactory.openSession();
            try {
                if (group.id() == null || group.id().trim().isEmpty()) {
                    return List.of();
                }
                Integer groupId = Integer.parseInt(group.id());

                TypedQuery<ContactRecord> query = session.createQuery(
                        "SELECT c FROM ContactRecord c JOIN c.groups g WHERE g.id = :groupId",
                        ContactRecord.class
                );
                query.setParameter("groupId", groupId);

                return convertContactList(query.getResultList());
            } catch (NumberFormatException e) {
                return List.of();
            }
    }

    public void createContact(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(contactData));
            session.getTransaction().commit();
        });
    }

    public Long getCountContacts() {
        Session session = sessionFactory.openSession();
        try {
            TypedQuery<Long> query = session.createQuery("SELECT COUNT (*) FROM ContactRecord", Long.class);
            return query.getSingleResult();
        } finally {
            session.close();
        }
    }
}