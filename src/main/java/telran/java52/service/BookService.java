package telran.java52.service;

import telran.java52.book.dto.BookDto;

public interface BookService {
	Boolean addBook (BookDto bookDto);
	BookDto findByIsbn(String isbn);
}
