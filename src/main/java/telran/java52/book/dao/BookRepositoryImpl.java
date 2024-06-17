package telran.java52.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import telran.java52.book.model.Author;
import telran.java52.book.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Book> findByAuthorsName(String author) {
		return em.createQuery("select b from Book b join b.authors a where a.name = ?1", Book.class)
				.setParameter(1, author).getResultStream();
	}

	@Override
	public Stream<Book> findByPublisherPublisherName(String publisher) {

		return em.createQuery("select b from Book b join b.publisher p where p.publisherName=?1", Book.class)
				.setParameter(1, publisher).getResultStream();
	}

	@Override
	public Stream<Author> findBookAuthors(String isbn) {
		return em.createQuery("SELECT b.authors FROM Book b WHERE b.isbn = ?1", Author.class)
				.setParameter(1, isbn).getResultStream();
	}

	@Override
	public Stream<String> findPublishersByAuthor(String author) {
		TypedQuery<String> query = em.createQuery(
				"SELECT DISTINCT p.publisherName FROM Book b join b.authors  a join b.publisher p  WHERE a.name = ?1",
				String.class);
		query.setParameter(1, author);

		return query.getResultStream();
	}

	@Override
	public void deleteByAuthorsName(String name) {
		em.createQuery("delete from Book b where ?1 members of b.authors").setParameter(1, name).executeUpdate();

	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(em.find(Book.class, isbn));
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public void deleteById(String isbn) {
		em.remove(em.find(Book.class, isbn));
	}

}
