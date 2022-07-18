package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;

/**
 * Modified by Pierrot on 7/18/22.
 */
public interface AuthorDao {
    Author findAuthorById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
