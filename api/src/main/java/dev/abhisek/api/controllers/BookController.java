package dev.abhisek.api.controllers;

import dev.abhisek.api.dto.BookRequest;
import dev.abhisek.api.dto.BookResponse;
import dev.abhisek.api.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("featured")
    public ResponseEntity<List<BookResponse>> featured() {
        return ResponseEntity.ok(bookService.getAllFeaturedBook());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("{id}/picture")
    public ResponseEntity<String> getPicture(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookPictureById(id));
    }

    @GetMapping("search")
    public ResponseEntity<List<BookResponse>> search(@RequestParam String q) {
        return ResponseEntity.ok(bookService.searchBook(q));
    }

    @PostMapping
    public ResponseEntity<Void> createBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String description,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            @RequestParam MultipartFile image
    ) {
        BookRequest request = new BookRequest(title, author, description, price, quantity);
        bookService.addBook(request, image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{id}/featured")
    public ResponseEntity<Void> featured(@PathVariable String id) {
        bookService.featureBook(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
