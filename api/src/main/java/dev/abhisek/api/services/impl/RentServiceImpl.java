package dev.abhisek.api.services.impl;

import dev.abhisek.api.dto.BookResponse;
import dev.abhisek.api.dto.RentRequest;
import dev.abhisek.api.dto.RentResponse;
import dev.abhisek.api.dto.UserResponse;
import dev.abhisek.api.entities.Book;
import dev.abhisek.api.entities.Rent;
import dev.abhisek.api.entities.User;
import dev.abhisek.api.exceptions.BadRequestException;
import dev.abhisek.api.exceptions.NotFoundException;
import dev.abhisek.api.repo.BookRepository;
import dev.abhisek.api.repo.RentRepository;
import dev.abhisek.api.repo.UserRepository;
import dev.abhisek.api.services.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {
    private final BookRepository bookRepository;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void rent(List<RentRequest> requests, User user) {
        requests
                .forEach(request -> {
                    Book book = bookRepository.findById(request.id())
                            .orElseThrow(() -> new NotFoundException("Book not found"));
                    if (book.getQuantity() < request.quantity()) {
                        throw new BadRequestException("Book quantity exceeds");
                    }
                    if (rentRepository.existsByBookAndUserAndReturnedDateNull(book, user)) {
                        throw new BadRequestException("Rent already exists for the book " + book.getTitle());
                    }
                    book.setQuantity(book.getQuantity() - request.quantity());
                    bookRepository.save(book);
                    Rent rent = new Rent();
                    rent.setBook(book);
                    rent.setUser(user);
                    rent.setQuantity(request.quantity());
                    rent.setFine(0);
                    rentRepository.save(rent);
                });
    }

    @Override
    public List<RentResponse> getAllUserRents(User user) {
        return rentRepository.findAllByUser(user)
                .stream().map(this::toResponse)
                .toList();
    }

    @Override
    public List<RentResponse> getAllBookRents(String id) {
        return bookRepository.findById(id)
                .map(book -> rentRepository
                        .findAllByBook(book).stream()
                        .map(this::toResponse)
                        .toList())
                .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @Override
    public List<RentResponse> getAllUserRents(String userId) {
        return userRepository.findById(userId)
                .map(User::getRents)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public void returnBook(List<Integer> ids) {
        ids.forEach(
                id -> rentRepository.findById(id)
                        .ifPresentOrElse(
                                (rent) -> {
                                    Book book = rent.getBook();
                                    book.setQuantity(rent.getBook().getQuantity() + rent.getQuantity());
                                    bookRepository.save(book);
                                    rent.setReturnedDate(new Date());
                                    rentRepository.save(rent);
                                },
                                () -> {
                                    throw new NotFoundException("Book not found");
                                }
                        )
        );
    }

    private RentResponse toResponse(Rent rent) {
        return new RentResponse(
                rent.getId(),
                new UserResponse(rent.getUser().getId(), rent.getUser().getName()),
                toBookResponse(rent.getBook()),
                rent.getRentedDate().toString(),
                rent.getReturnedDate() == null ? null : rent.getReturnedDate().toString(),
                rent.getFine(),
                rent.getQuantity()
        );
    }

    private List<RentResponse> toResponse(List<Rent> rents) {
        return rents.stream()
                .map(this::toResponse)
                .toList();
    }

    private BookResponse toBookResponse(Book book) {
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
}
