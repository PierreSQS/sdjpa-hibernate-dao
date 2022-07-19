package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * Modified by Pierrot on 7/19/22.
 */
@Component
public class BookDaoImpl implements BookDao {

    public static final String BOOK_BY_ISNB =
            "SELECT b FROM Book b WHERE b.isbn = :isbn";

    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> typedQuery = em.createQuery(BOOK_BY_ISNB,Book.class);
            typedQuery.setParameter("isbn",isbn);
            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }

    }

    @Override
    public Book findBookById(Long id) {
        return getEntityManager().find(Book.class,id);
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> typedQuery = getEntityManager().createNamedQuery("find_book_by_title",Book.class);
            typedQuery.setParameter("title",title);
            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }

    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.flush();
            em.getTransaction().commit();
            return book;
        } finally {
            em.close();
        }

    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(book);
            em.flush();
            em.clear();
            em.getTransaction().commit();
            return book;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        try {
            Book foundBook = em.find(Book.class, id);
            em.getTransaction().begin();
            if (foundBook != null){
                em.remove(foundBook);
                em.flush();
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @Override
    public List<Book> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> typedQuery = em.createNamedQuery("find_all_book",Book.class);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }


    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
