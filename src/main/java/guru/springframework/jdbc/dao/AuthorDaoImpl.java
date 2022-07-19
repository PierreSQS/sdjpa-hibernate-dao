package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot on 7/18/22.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    public static final String AUTHOR_BY_FIRST_NAME_AND_LAST_NAME =
            "SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name";
    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
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
        TypedQuery<Author> typedQuery = em.createQuery(
                AUTHOR_BY_FIRST_NAME_AND_LAST_NAME,Author.class);
        typedQuery.setParameter("first_name",firstName);
        typedQuery.setParameter("last_name",lastName);
        em.close();
        return typedQuery.getSingleResult();
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
