package dev.abhisek.api.repo;

import dev.abhisek.api.entities.Book;
import dev.abhisek.api.entities.Rent;
import dev.abhisek.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentRepository extends JpaRepository<Rent, Integer> {
    List<Rent> findAllByUser(User user);

    List<Rent> findAllByBook(Book book);

    Boolean existsByBookAndUserAndReturnedDateNull(Book book, User user);

}
