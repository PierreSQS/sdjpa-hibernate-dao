package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

/**
 * Modified by Pierrot on 24-02-2025.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManager em;

    public AuthorDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author getById(Long id) {
        Author author = em.find(Author.class, id);
        em.close();
        return author;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a " +
                "WHERE a.firstName = :first_name and a.lastName = :last_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        Author author = query.getSingleResult();
        em.close();
        return author;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        em.getTransaction().begin();
        em.persist(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        try {
            em.joinTransaction();
            em.merge(author);
            em.flush();
            em.clear();
            return em.find(Author.class, author.getId());
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteAuthorById(Long id) {
        em.getTransaction().begin();
        Author author = em.find(Author.class, id);
        em.remove(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

}
















