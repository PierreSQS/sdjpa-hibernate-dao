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
        return getEntityManager().find(Author.class,id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> typedQuery = getEntityManager().createQuery(
                AUTHOR_BY_FIRST_NAME_AND_LAST_NAME,Author.class);
        typedQuery.setParameter("first_name",firstName);
        typedQuery.setParameter("last_name",lastName);
        return typedQuery.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
