package telran.java52.service;

import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;

public interface BookService {
	Boolean addBook(BookDto bookDto);

	BookDto findByIsbn(String isbn);

	BookDto removeByIsbn(String isbn);

	BookDto updateBookByIsbn(String isbn, String title);

	BookDto[] findBooksByAuthor(String author);

	BookDto[] findBooksByPublisher(String publisher);

	AuthorDto[] findBookAuthors(String isbn);

	String[] findPublishersByAuthor(String author);

	AuthorDto removeAuthor(String author);

}
