package dev.abhisek.api.services;

import dev.abhisek.api.dto.BookRequest;
import dev.abhisek.api.dto.BookResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookResponse> getAllFeaturedBook();

    BookResponse getBookById(String id);

    String getBookPictureById(String id);

    List<BookResponse> searchBook(String query);

    @PreAuthorize("hasAuthority('ADMIN')")
    void addBook(BookRequest request, MultipartFile image);

    @PreAuthorize("hasAuthority('ADMIN')")
    void featureBook(String id);

    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteBook(String id);
}
