package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

/**
 * Modified by Pierrot on 7/19/22.
 */
@Component
public class BookDaoImpl implements BookDao {
    public static final String BOOK_BY_TITLE =
            "SELECT b FROM Book b WHERE b.title = :title";

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
        TypedQuery<Book> typedQuery = getEntityManager().createQuery(BOOK_BY_TITLE,Book.class);
        typedQuery.setParameter("title",title);
        return typedQuery.getSingleResult();
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        Book foundBook = em.find(Book.class, id);
        em.getTransaction().begin();
        if (foundBook != null){
            em.remove(foundBook);
            em.flush();
        }
        em.getTransaction().commit();

    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
