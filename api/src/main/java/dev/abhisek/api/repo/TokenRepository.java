package dev.abhisek.api.repo;

import dev.abhisek.api.entities.Token;
import dev.abhisek.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,String > {

    Token findByUser(User user);
}
