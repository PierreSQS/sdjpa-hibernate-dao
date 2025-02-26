package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import java.util.List;

/**
 * Modified by Pierrot on 26-02-2025.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory emf;

    public AuthorDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public List<Author> findAll() {

        try (EntityManager em = getEntityManager()) {
            TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all", Author.class);

            return typedQuery.getResultList();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLike(String lastName) {

        try (EntityManager em = getEntityManager()) {
            Query query = em.createQuery("SELECT a from Author a where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            return query.getResultList();
        }
    }

    @Override
    public Author getById(Long id) {
        EntityManager em = getEntityManager();
        Author author = getEntityManager().find(Author.class, id);
        em.close();
        return author;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager em = getEntityManager();

        TypedQuery<Author> query = em.createNamedQuery("find_by_name", Author.class);

        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        Author author = query.getSingleResult();
        em.close();
        return author;
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {

        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Author> cq = cb.createQuery(Author.class);
            Root<Author> root = cq.from(Author.class);

            Predicate firstNamePred = cb.equal(root.get("firstName"), firstName);
            Predicate lastNamePred = cb.equal(root.get("lastName"), lastName);

            cq.select(root).where(cb.and(firstNamePred, lastNamePred));

            TypedQuery<Author> query = em.createQuery(cq);
            return query.getSingleResult();
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

        try (EntityManager em = getEntityManager()) {
            em.joinTransaction();
            em.merge(author);
            em.flush();
            em.clear();
            return em.find(Author.class, author.getId());
        }
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Author author = em.find(Author.class, id);
        em.remove(author);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
















