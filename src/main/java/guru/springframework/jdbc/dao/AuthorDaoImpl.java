package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<Author> listAuthorByLastNameLike(String lastName) {
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :last_name", Author.class);
        query.setParameter("last_name", "%" + lastName + "%");
        return query.getResultList();
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

    @Transactional
    @Override
    public Author saveNewAuthor(Author author) {
        em.persist(author);
        em.close();
        return author;
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        try {
            em.merge(author);
            return em.find(Author.class, author.getId());
        } finally {
            em.close();
        }
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        Author author = em.find(Author.class, id);
        em.remove(author);
        em.close();
    }

}
















