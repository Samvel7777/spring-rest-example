package am.itspace.springrestexample.repository;

import am.itspace.springrestexample.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
