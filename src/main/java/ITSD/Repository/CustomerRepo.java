package ITSD.Repository;

import ITSD.Entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class CustomerRepo implements IRepository<Customer> {

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


    public List<Customer> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em
                    .createQuery("FROM Customer", Customer.class)
                    .getResultList();
        }
    }

    public Customer get(String id) {
        if (id != null)
            try
            { return get(Integer.parseInt(id)); }
            catch (NumberFormatException ex)
            { }
        return null;
    }

    public Customer get(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Customer.class, id);
        }
    }

    public void insert(Customer item) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(item);
            tx.commit();
        }
    }

    public void update(Customer item) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.merge(item);
            tx.commit();
        }
    }

    public void delete(Customer item) {
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
            Customer c = em.find(Customer.class, id);
            if (c != null) {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                em.remove(c);
                tx.commit();
            }
        }
    }
}
