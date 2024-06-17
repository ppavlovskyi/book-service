package telran.java52.book.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java52.book.model.Author;
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
	
	@PersistenceContext
	EntityManager em;
	

	@Override
	public Optional<Author> findById(String authorId) {
		return Optional.ofNullable(em.find(Author.class, authorId));
	}

	@Override
	public Author save(Author author) {
		em.persist(author);
		return author;
	}

	@Override
	public void deleteById(String authorId) {
		Author author = em.find(Author.class, authorId);
		if(author !=null) em.remove(author);

	}

}
