package ITSD.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class RepositoryStringPK<T extends IEntityStringPK> implements IRepository<T> {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    private final Class<T> entityClass;

    public RepositoryStringPK(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void close() {
    }

    @Override
    public List<T> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("FROM " + entityClass.getName(), entityClass)
                    .getResultList();
        }
    }

    @Override
    public T get(int id) {
        return get(Integer.toString(id));
    }

    @Override
    public T get(String id) {
        if (id == null) {
            return null;
        }
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(entityClass, id);
        }
    }

    @Override
    public void insert(T item) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(item);
            tx.commit();
        }
    }

    @Override
    public void update(T item) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(item);
            tx.commit();
        }
    }

    @Override
    public void delete(T item) {
        if (item != null) {
            delete(item.getId());
        }
    }

    @Override
    public void delete(int id) {
        delete(Integer.toString(id));
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            return;
        }
        try (EntityManager em = emf.createEntityManager()) {
            T item = em.find(entityClass, id);
            if (item != null) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.remove(item);
                tx.commit();
            }
        }
    }
}
