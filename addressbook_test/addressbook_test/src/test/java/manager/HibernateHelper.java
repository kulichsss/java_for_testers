package manager;

import io.qameta.allure.Step;
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
import java.util.stream.Collectors;

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
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    public static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    public GroupRecord convert(GroupData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), data.name(), data.header(), data.footer());
    }

    @Step
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

    @Step
    public void createGroup(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }

    public List<ContactData> convertContactList(List<ContactRecord> records) {
        List<ContactData> result = new ArrayList<>();
        for (var record : records) {
            result.add(new ContactData("" + record.id, record.firstname, record.middlename, record.lastname, record.photo, record.home, record.mobile, record.work, record.fax, record.address, record.email, record.email2, record.email3));
        }
        return result;
//        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());

    }

    public static ContactData convert(ContactRecord record) {
        return new ContactData("" + record.id, record.firstname, record.middlename, record.lastname, record.photo, record.home, record.mobile, record.work, record.fax, record.address, record.email, record.email2, record.email3);
    }

    public ContactRecord convert(ContactData data) {
        var id = data.id();
        if ("".equals(id)) {
            id = "0";
        }
        return new ContactRecord(Integer.parseInt(id), data.firstname(), data.middlename(), data.lastname(), data.photo(), data.home(), data.mobile(), data.work(), data.fax());
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
            try {
                session.getTransaction().begin();
                session.persist(convert(contactData));
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback(); // важно откатить транзакцию
                throw new RuntimeException("Ошибка при создании контакта", e);
            }
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

    public ContactData getLastAddContact(GroupData group) {
        Session session = sessionFactory.openSession();
        try {
            if (group.id() == null || group.id().trim().isEmpty()) {
                return null;
            }
            Integer groupId = Integer.parseInt(group.id());

            // Запрос: найти контакт в группе с максимальным значением created
            TypedQuery<ContactRecord> query = session.createQuery(
                    "SELECT cl.contact FROM ContactInGroupRecord cl " +
                            "WHERE cl.group.id = :groupId " +
                            "ORDER BY cl.created DESC",
                    ContactRecord.class
            );
            query.setParameter("groupId", groupId);
            query.setMaxResults(1); // только один результат — самый свежий

            List<ContactRecord> result = query.getResultList();
            if (result.isEmpty()) {
                return null;
            }

            return convert(result.get(0)); // конвертируем ContactRecord → ContactData
        } catch (NumberFormatException e) {
            return null;
        } finally {
            session.close();
        }
    }

    public Long getCountContactInGroup(GroupData group) {
        Session session = sessionFactory.openSession();
        try {
            Integer groupId = Integer.parseInt(group.id());
            TypedQuery<Long> query = session.createQuery("SELECT COUNT(cl) FROM ContactInGroupRecord cl " +
                            "WHERE cl.group.id = :groupId",
                    Long.class
            );
            query.setParameter("groupId", groupId);
            return query.getSingleResult();
        } finally {
            session.close();
        }
    }

    public List<ContactData> findContactNotInGroup(GroupData group) {
        Session session = sessionFactory.openSession();
        try {
            Integer groupId = Integer.parseInt(group.id());

            TypedQuery<ContactRecord> query = session.createQuery(
                    "SELECT c FROM ContactRecord c " +
                            "WHERE c.id NOT IN (" +
                            "    SELECT cl.contact.id FROM ContactInGroupRecord cl " +
                            "    WHERE cl.group.id = :groupId" +
                            ")",
                    ContactRecord.class
            );
            query.setParameter("groupId", groupId);

            List<ContactRecord> records = query.getResultList();
            return convertContactList(records);
        } catch (NumberFormatException e) {
            return List.of();
        } finally {
            session.close();
        }
    }
}