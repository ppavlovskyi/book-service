package telran.java52.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java52.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {

		return em.createQuery(
				"select distinct p.publisherName from Book b join b.publisher p join b.authors a WHERE a.name = ?1",
				Publisher.class).setParameter(1, authorName).getResultStream();
	}

	@Override
	public Optional<Publisher> findById(String publisherName) {
		return Optional.ofNullable(em.find(Publisher.class, publisherName));
	}

	@Override
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
		return publisher;
	}

}
