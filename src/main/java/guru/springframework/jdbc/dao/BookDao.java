package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

/**
 * Modified by Pierrot on 7/19/22.
 */
public interface BookDao {
    Book findByISBN(String isbn);

    Book findBookById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
