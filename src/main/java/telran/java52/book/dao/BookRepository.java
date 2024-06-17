package telran.java52.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;

import telran.java52.book.model.Author;
import telran.java52.book.model.Book;

public interface BookRepository  {

//	@Query(value = "select b from Book b join b.authors a where a.name = ?1")
//	Stream<Book> findBooksByAuthor(String author);
	
	Stream<Book> findByAuthorsName(String author);

//	@Query(value = "select b from Book b join b.publisher p where p.publisherName=?1")
//	Stream<Book> findBooksByPublisher(String publisher);
	

	Stream<Book> findByPublisherPublisherName(String publisher);

	@Query("SELECT b.authors FROM Book b WHERE b.isbn = :isbn")
	Stream<Author> findBookAuthors(String isbn);

	@Query("SELECT DISTINCT p.publisherName FROM Book b join b.authors  a join b.publisher p  WHERE a.name = ?1")
	Stream<String> findPublishersByAuthor(String author);
	
	void deleteByAuthorsName(String name);

	boolean existsById(String isbn);

	Optional<Book> findById(String isbn);

	Book save(Book book);

	void deleteById(String isbn);
}
