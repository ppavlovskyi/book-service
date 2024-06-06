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
		
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher()).orElse(publisherRepository.save(new Publisher(bookDto.getPublisher()) ));
		
		Set<Author> authors = bookDto.getAuthors().stream()
			    .map(a -> authorRepository.findById(a.getName())
			        .orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate())))
			    ).collect(Collectors.toSet());
		
		
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher );
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		
		return modelMapper.map(book, BookDto.class);
	}

}
