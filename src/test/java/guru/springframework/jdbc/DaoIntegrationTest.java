package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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
    void testFindAllAuthors() {
        List<Author> authors = authorDao.findAll();

        assertThat(authors)
                .isNotNull()
                .isNotEmpty();

        System.out.printf("%n###### all authors in test ######%n");
        authors.forEach(author -> System.out.println(author.getFirstName()+" "+author.getLastName()));
        System.out.println();
    }

    @Test
    void testFindBookByISBN() {
        Book book = new Book();
        book.setIsbn("1234" + RandomString.make());
        book.setTitle("ISBN TEST");

        Book saved = bookDao.saveNewBook(book);

        Book fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testListAuthorByLastNameLike() {
        List<Author> authors = authorDao.listAuthorByLastNameLike("Wall");

        assertThat(authors)
                .isNotNull()
                .isNotEmpty();

        System.out.printf("%n###### following the authors in test ######%n");
        authors.forEach(author -> System.out.println(author.getFirstName()+" "+author.getLastName()));
        System.out.println();
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        Book deleted = bookDao.findBookById(saved.getId());

        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        Book updatedBook = bookDao.updateBook(saved);

        Book fetched = bookDao.findBookById(updatedBook.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByTitle() {
        Book foundBook = bookDao.findBookByTitle("Clean Code");

        assertThat(foundBook).isNotNull();
        System.out.printf("%n###### the found Book name: %s ######%n%n", foundBook.getTitle());

    }

    @Test
    void testGetBook() {
        Book book = bookDao.findBookById(3L);

        assertThat(book.getId()).isNotNull();
    }

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
    void testFindAllBooks() {
        List<Book> books = bookDao.findAll();
        assertThat(books)
                .isNotEmpty().
                isNotNull();

        System.out.printf("%n###### following the authors in test ######%n");
        books.forEach(book -> System.out.println(book.getTitle()+", "+book.getPublisher()));
        System.out.println();

    }

    @Test
    void testFindBookByTitle() {
        Book book = new Book();
        book.setIsbn("1235" + net.bytebuddy.utility.RandomString.make());
        book.setTitle("TITLE TEST2");

        Book saved = bookDao.saveNewBook(book);

        Book fetched = bookDao.findBookByTitle(book.getTitle());
        assertThat(fetched).isNotNull();

        bookDao.deleteBookById(saved.getId());
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

}
