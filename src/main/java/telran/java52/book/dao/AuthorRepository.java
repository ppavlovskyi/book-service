package telran.java52.book.dao;

import java.util.Optional;

import telran.java52.book.model.Author;

public interface AuthorRepository {

	Optional<Author> findById(String authorId);

	Author  save(Author author);

	void deleteById(String authorId);
	
	
	

}
