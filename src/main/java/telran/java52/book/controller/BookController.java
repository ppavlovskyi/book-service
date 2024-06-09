package telran.java52.book.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

	final BookService bookService;

	@PostMapping("/book")
	public Boolean addBook(@RequestBody BookDto bookDto) {

		return bookService.addBook(bookDto);
	}

	@GetMapping("/book/{isbn}")
	public BookDto findByIsbn(@PathVariable String isbn) {

		return bookService.findByIsbn(isbn);
	}

	@DeleteMapping("/book/{isbn}")
	public BookDto removeByIsbn(@PathVariable String isbn) {
		return bookService.removeByIsbn(isbn);
	}

	@PutMapping("/book/{isbn}/title/{title}")
	public BookDto updateBookByIsbn(@PathVariable String isbn, @PathVariable String title) {
		return bookService.updateBookByIsbn(isbn, title);
	}

	@GetMapping("/books/author/{author}")
	public BookDto[] findBooksByAuthor(@PathVariable String author) {
		return bookService.findBooksByAuthor(author);
	}

	@GetMapping("/books/publisher/{publisher}")
	public BookDto[] findBooksByPublisher(@PathVariable String publisher) {
		return bookService.findBooksByPublisher(publisher);
	}

	@GetMapping("/authors/book/{isbn}")
	public AuthorDto[] findBookAuthors(@PathVariable String isbn) {
		return bookService.findBookAuthors(isbn);
	}

	@GetMapping("/publishers/author/{author}")
	public String[] findPublishersByAuthor(@PathVariable String author) {
		return bookService.findPublishersByAuthor(author);
	}

	@DeleteMapping("/author/{author}")
	public AuthorDto removeAuthor(@PathVariable String author) {
		return bookService.removeAuthor(author);
	}

}
