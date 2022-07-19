package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Modified by Pierrot on 7/18/22.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    public static final String AUTHOR_BY_FIRST_NAME_AND_LAST_NAME =
            "SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name";

    public static final String ALL_AUTHORS_BY_LAST_NAME =
            "SELECT a FROM Author a WHERE a.lastName LIKE :last_name";

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Author> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all",Author.class);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }

    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {
        EntityManager em = emf.createEntityManager();
        try {
           TypedQuery<Author> query = em.createQuery(ALL_AUTHORS_BY_LAST_NAME, Author.class);
           query.setParameter("last_name",lastName+"%");
           return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author findAuthorById(Long id) {
        EntityManager em = getEntityManager();
        Author foundAuthor = em.find(Author.class, id);
        em.close();
        return foundAuthor;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Author> typedQuery = em.createQuery(
                    AUTHOR_BY_FIRST_NAME_AND_LAST_NAME,Author.class);
            typedQuery.setParameter("first_name",firstName);
            typedQuery.setParameter("last_name",lastName);
            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }

    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(author);
        em.flush();
        em.clear();
        em.close();
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Author author = em.find(Author.class,id);
        em.remove(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
