package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

/**
 * Modified by Pierrot on 25-02-2025.
 */
public interface BookDao {
    Book findByISBN(String isbn);

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
