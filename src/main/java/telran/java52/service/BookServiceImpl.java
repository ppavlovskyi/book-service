package telran.java52.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java52.book.dao.AuthorRepository;
import telran.java52.book.dao.BookRepository;
import telran.java52.book.dao.PublisherRepository;
import telran.java52.book.dto.AuthorDto;
import telran.java52.book.dto.BookDto;
import telran.java52.book.dto.exeption.EntityNotFoundException;
import telran.java52.book.model.Author;
import telran.java52.book.model.Book;
import telran.java52.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Transactional
	@Override
	public Boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}

		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElseGet(()->publisherRepository.save(new Publisher(bookDto.getPublisher())));

		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());

		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);

		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto removeByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.deleteById(isbn);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional
	@Override
	public BookDto updateBookByIsbn(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		return modelMapper.map(book, BookDto.class);
	}

	@Transactional(readOnly = true)
	@Override
	public BookDto[] findBooksByAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
		return author.getBooks().stream()
				.map(b -> modelMapper.map(b, BookDto.class))
				.toArray(BookDto[]::new);
//		return bookRepository.findByAuthorsName(authorName).map(b -> modelMapper.map(b, BookDto.class))
//				.toArray(BookDto[]::new);

	}

	@Transactional(readOnly = true)
	@Override
	public BookDto[] findBooksByPublisher(String publisherName) {
		Publisher publisher = publisherRepository.findById(publisherName).orElseThrow(EntityNotFoundException::new);
		return publisher.getBooks().stream().map(b -> modelMapper.map(b, BookDto.class))
				.toArray(BookDto[]::new);
		
//		return bookRepository.findByPublisherPublisherName(publisherName).map(b -> modelMapper.map(b, BookDto.class))
//				.toArray(BookDto[]::new);
	}

	@Transactional(readOnly = true)
	@Override
	public AuthorDto[] findBookAuthors(String isbn) {
//	    return bookRepository.findBookAuthors(isbn)
//	            .map(a -> modelMapper.map(a, AuthorDto.class))
//	            .toArray(AuthorDto[]::new);
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).toArray(AuthorDto[]::new);
	}

	@Transactional(readOnly = true)
	@Override
	public String[] findPublishersByAuthor(String authorName) {
		return publisherRepository.findDistinctByBooksAuthorsName(authorName).map(Publisher::getPublisherName).toArray(String[]::new);
		
//		return bookRepository.findPublishersByAuthor(author)
//	            .toArray(String[]::new);
//		return publisherRepository.findPublishersByAuthor(authorName).toArray(String[]::new);
	}

	@Transactional
	@Override
	public AuthorDto removeAuthor(String authorId) {
		Author author = authorRepository.findById(authorId).orElseThrow(EntityNotFoundException::new);
//		bookRepository.findByAuthorsName(authorId).forEach(b -> bookRepository.delete(b));
//		bookRepository.deleteByAuthorsName(authorId);
		authorRepository.deleteById(authorId);
		return modelMapper.map(author, AuthorDto.class);
	}

}
