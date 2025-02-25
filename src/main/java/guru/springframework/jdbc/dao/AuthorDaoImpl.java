package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

/**
 * Modified by Pierrot on 7/18/22.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Author findAuthorById(Long id) {
        return getEntityManager().find(Author.class,id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return getEntityManager().createQuery("FROM Author a WHERE a.firstName = :firstName " +
                                                      "and a.lastName = :lastName", Author.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        // Get the EntityManager
        EntityManager entityManager = getEntityManager();

        // Save the Author
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.getTransaction().commit();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        // Get EntityManager
        EntityManager entityManager = getEntityManager();

        // Update the Author
        entityManager.getTransaction().begin();
        entityManager.merge(author);
        entityManager.getTransaction().commit();

        // Return the updated Author
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
