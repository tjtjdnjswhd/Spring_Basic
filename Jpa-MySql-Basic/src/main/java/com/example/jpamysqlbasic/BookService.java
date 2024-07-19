package com.example.jpamysqlbasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = bookDTO.toEntity();
        Book savedBook = bookRepository.save(book);
        return BookDTO.fromEntity(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookDTO::fromEntity);
    }

    @Transactional
    public Optional<BookDTO> updateBook(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDTO.getTitle());
                    existingBook.setAuthor(bookDTO.getAuthor());
                    existingBook.setIsbn(bookDTO.getIsbn());
                    existingBook.setPrice(bookDTO.getPrice());
                    existingBook.setStock(bookDTO.getStock());
                    return BookDTO.fromEntity(bookRepository.save(existingBook));
                });
    }

    @Transactional
    public boolean deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return true;
                })
                .orElse(false);
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(BookDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByPriceRange(Double minPrice, Double maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getBooksWithStockGreaterThan(Integer minStock) {
        return bookRepository.findByStockGreaterThanEqual(minStock).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByTitleContaining(String keyword) {
        return bookRepository.findByTitleContaining(keyword).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }
}