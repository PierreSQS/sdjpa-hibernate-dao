package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.List;

/**
 * Modified by Pierrot on 26-02-2025.
 */
@Component
public class BookDaoImpl implements BookDao {
    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book findByISBN(String isbn) {

        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);

            return query.getSingleResult();
        }
    }

    @Override
    public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book = getEntityManager().find(Book.class, id);
        em.close();
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {

        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createNamedQuery("find_by_title", Book.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        }
    }

    @Override
    public Book findBookByTitleCriteria(String title) {

        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Book> cq = cb.createQuery(Book.class);
            Root<Book> root = cq.from(Book.class);

            cq.select(root).where(cb.equal(root.get("title"), title));

            TypedQuery<Book> query = em.createNamedQuery("find_by_title", Book.class);
            query.setParameter("title", title);

            return query.getSingleResult();
        }
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        Book savedBook = em.find(Book.class, book.getId());
        em.getTransaction().commit();
        em.close();
        return savedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Book> findAll() {

        try (EntityManager em = getEntityManager()) {
            TypedQuery<Book> query = em.createNamedQuery("find_all_books", Book.class);

            return query.getResultList();
        }
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}





