package ITSD.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class RepositoryIntPK<T extends IEntityIntPK> implements IRepository<T> {

    // we need to know the Class of the Entity we are working with
    final Class<T> entityClass;

    public RepositoryIntPK(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private static final String PERSISTENCE_UNIT = "default";

    // WARNING: If there is a problem with the database connection,
    // then the constructor throws an exception
    // if you are using the Repo as a class-level field in another class (or Servlet/JSP)
    // then the exception is thrown when the Servlet/JSP is created
    // which then LOOKS like a JSP issue, when it is in fact a database issue
    //
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    @Override
    public void close() throws Exception {
//        if (emf.isOpen())
//            emf.close();
    }


    public List<T> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            List<T> list = em
                    .createQuery("FROM " + entityClass.getName(), entityClass)
                    .getResultList();
            list.forEach(this::initializeAssociations);
            return list;
        }
    }

    public T get(String id) {
        if (id != null)
            try
            { return get(Integer.parseInt(id)); }
            catch (NumberFormatException ex)
            { }
        return null;
    }

    public T get(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            T entity = em.find(entityClass, id);
            initializeAssociations(entity);
            return entity;
        }
    }

    private void initializeAssociations(T entity) {
        if (entity == null) {
            return;
        }
        try {
            for (var field : entityClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (value instanceof List<?> list) {
                    list.size();
                    for (Object item : list) {
                        if (item != null) {
                            item.toString();
                        }
                    }
                }
            }
        } catch (IllegalAccessException ignored) {
        }
    }

    public void insert(T item) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(item);
            tx.commit();
        }
    }

    public void update(T item) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(item);
            tx.commit();
        }
    }

    public void delete(T item) {
        delete(item.getId());
    }

    public void delete(String id) {
        if (id != null)
            try
            { delete(Integer.parseInt(id)); }
            catch (NumberFormatException ex)
            { }
    }

    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            T c = em.find(entityClass, id);
            if (c != null) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.remove(c);
                tx.commit();
            }
        }
    }
}
