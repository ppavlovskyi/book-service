package telran.java52.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import telran.java52.book.model.Publisher;

public interface PublisherRepository {

//	@Query("select distinct p.publisherName from Book b join b.publisher p join b.authors a WHERE a.name = ?1")
//	List<String> findPublishersByAuthor(String author);
	
	Stream<Publisher> findDistinctByBooksAuthorsName(String authorName);

	Optional<Publisher> findById(String publisherName);

	Publisher save(Publisher publisher);
	
}
