package am.itspace.springrestexample.endpoint;

import am.itspace.springrestexample.exception.ResourceNotFoundException;
import am.itspace.springrestexample.model.Book;
import am.itspace.springrestexample.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book getById(@PathVariable("id") int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book dose not exists!"));
    }

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book) {
        if (book.getId() > 0) {
            throw new RuntimeException("Id must be 0");
        }
        return bookRepository.save(book);
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable("id") int id) {
        Book bookFromDB = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book dose not exists!"));
        bookFromDB.setTitle(book.getTitle());
        bookFromDB.setDescription(book.getDescription());
        bookFromDB.setPrice(book.getPrice());
        bookFromDB.setAuthorName(book.getAuthorName());
        return bookRepository.save(bookFromDB);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBooks(@PathVariable("id") int id) {
        bookRepository.delete(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book dose not exists!")));
    }
}
