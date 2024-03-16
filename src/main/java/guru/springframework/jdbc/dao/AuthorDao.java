package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;

import java.util.List;

/**
 * Modified by Pierrot on 7/18/22.
 */
public interface AuthorDao {
    List<Author> findAll();

    List<Author> listAuthorByLastNameLike(String lastName);

    Author findAuthorById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    Author findAuthorByNameCriteria(String firstName, String lastName);
}
