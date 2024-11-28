package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Modified by Pierrot on 11/28/24.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {
    public static final String AUTHOR_BY_FIRST_NAME_AND_LAST_NAME =
            "SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name";
    private final EntityManager em;

    public AuthorDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author findAuthorById(Long id) {
        return em.find(Author.class,id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> typedQuery = em.createQuery(
                AUTHOR_BY_FIRST_NAME_AND_LAST_NAME,Author.class);
        typedQuery.setParameter("first_name",firstName);
        typedQuery.setParameter("last_name",lastName);
        return typedQuery.getSingleResult();
    }

    @Transactional
    @Override
    public Author saveNewAuthor(Author author) {
        em.persist(author);
        return author;
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        em.merge(author);
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

}
