package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Modified by Pierrot on 7/18/22.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan("guru.springframework.jdbc.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DaoIntegrationTest {
    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        System.out.printf("%n###### the author to delete: %s %s ######%n%n"
                , author.getFirstName(), author.getLastName());

        Author saved = authorDao.saveNewAuthor(author);
        Long id = saved.getId();

        authorDao.deleteAuthorById(id);
        Author deleted = authorDao.findAuthorById(id);

        assertThat(deleted).isNull();
        // Check that the Author is effectively deleted from the DB
        assertThat(authorDao.findAuthorById(id)).isNull();

    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");

        System.out.printf("%n###### the author to update: %s %s ######%n%n"
                , author.getFirstName(), author.getLastName());

        Author saved = authorDao.saveNewAuthor(author);

        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Thompson");

        System.out.printf("%n###### the updated author name: %s %s -ID: %d ######%n%n"
                , updated.getFirstName(), updated.getLastName(),updated.getId());
    }

    @Test
    void testInsertAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();

        System.out.printf("%n###### the saved author name: %s %s -ID: %s ######%n%n"
                , author.getFirstName(), author.getLastName(), author.getId());
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");

        assertThat(author).isNotNull();
        System.out.printf("%n###### the found author name: %s ######%n%n", author.getLastName());
    }

    @Test
    void testGetAuthorById() {
        Author author = authorDao.findAuthorById(2L);
        assertThat(author.getId()).isNotNull();
        System.out.printf("%n###### the found author name: %s ######%n%n", author.getLastName());

    }

//    @Test
//    void testGetBookByTitle() {
//        Book foundBook = bookDao.findBookByTitle("Spring in Action, 6th Edition");
//        assertThat(foundBook).isNotNull();
//        System.out.printf("%n###### the found Book name: %s ######%n%n", foundBook.getTitle());
//    }
//
//    @Test
//    void testGetBookByTitleBookNotFound() {
//        assertThrows(EmptyResultDataAccessException.class,
//                () -> bookDao.findBookByTitle("Spring in Action, 6t Edition"));
//    }
}
