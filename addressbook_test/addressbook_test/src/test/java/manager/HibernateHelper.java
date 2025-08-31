package manager;

import jakarta.persistence.TypedQuery;
import manager.hbm.GroupRecord;
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
}