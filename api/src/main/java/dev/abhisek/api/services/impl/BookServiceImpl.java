package dev.abhisek.api.services.impl;

import dev.abhisek.api.dto.BookRequest;
import dev.abhisek.api.dto.BookResponse;
import dev.abhisek.api.entities.Book;
import dev.abhisek.api.exceptions.BadRequestException;
import dev.abhisek.api.exceptions.InternalServerErrorException;
import dev.abhisek.api.exceptions.NotFoundException;
import dev.abhisek.api.repo.BookRepository;
import dev.abhisek.api.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookResponse> getAllFeaturedBook() {
        return toResponse(bookRepository.findAllByFeaturedTrue());
    }

    @Override
    public BookResponse getBookById(String id) {
        return bookRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public String getBookPictureById(String id) {
        return bookRepository.findById(id)
                .map(book -> retrieveImage(book.getImage()))
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public List<BookResponse> searchBook(String query) {
        return bookRepository.findAllByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIdContainingIgnoreCase(query, query, query)
                .stream().map(this::toResponse)
                .toList();
    }

    @Override
    public void addBook(BookRequest request, MultipartFile image) {
        Optional<Book> optionalBook = bookRepository.findByTitle(request.title());
        if (optionalBook.isPresent()) {
            throw new BadRequestException("Book with title " + request.title() + " already exists");
        }
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setDescription(request.description());
        book.setFeatured(false);
        book.setPrice(request.price());
        book.setQuantity(request.quantity());
        String path = saveImage(image, book.getId());
        book.setImage(path);
        bookRepository.save(book);
    }

    @Override
    public void featureBook(String id) {
        bookRepository.findById(id)
                .ifPresentOrElse(
                        book -> {
                            book.setFeatured(!book.getFeatured());
                            bookRepository.save(book);
                        },
                        () -> {
                            throw new NotFoundException("Book not found");
                        }
                );
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPrice(),
                book.getQuantity(),
                book.getFeatured()
        );
    }

    private List<BookResponse> toResponse(List<Book> books) {
        return books.stream()
                .map(this::toResponse)
                .toList();
    }

    private String saveImage(MultipartFile image, String id) {
        final String dir = File.separator + "uploads" + File.separator;
        final String extension = getFileExtension(image.getOriginalFilename());
        final String fileName = id + extension;
        File file = new File(dir);
        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (!created) {
                throw new InternalServerErrorException("Some error occurred while creating the directory");
            }
        }
        final String filePath = dir + fileName;
        try {
            Path path = Paths.get(filePath);
            Files.write(path, image.getBytes());
            return filePath;
        } catch (IOException e) {
            throw new InternalServerErrorException("Some error occurred while creating the file");
        }

    }

    private String retrieveImage(String fileName) {
        try {
            Path path = Paths.get(fileName);
            byte[] bytes = Files.readAllBytes(path);
            StringBuilder url = new StringBuilder("data:");
            final String mimeType = switch (getFileExtension(fileName)) {
                case ".jpg", ".jpeg" -> "image/jpeg";
                case ".png" -> "image/png";
                default -> "";
            };
            url.append(mimeType);
            url.append(";");
            url.append("base64,");
            url.append(Base64.getEncoder().encodeToString(bytes));
            return url.toString();
        } catch (IOException e) {
            throw new InternalServerErrorException("Some error occurred while reading the file");
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
