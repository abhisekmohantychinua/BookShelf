package dev.abhisek.api.repo;

import dev.abhisek.api.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String > {
    List<Book> findAllByFeaturedTrue();
    Optional<Book> findByTitle(String title);
    List<Book> findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIdContainingIgnoreCase(String title, String author, String id);
}
