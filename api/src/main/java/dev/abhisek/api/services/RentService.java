package dev.abhisek.api.services;

import dev.abhisek.api.dto.RentRequest;
import dev.abhisek.api.dto.RentResponse;
import dev.abhisek.api.entities.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface RentService {
    void rent(List<RentRequest> requests, User user);

    List<RentResponse> getAllUserRents(User user);

    @PreAuthorize("hasAuthority('ADMIN')")
    List<RentResponse> getAllBookRents(String id);
    @PreAuthorize("hasAuthority('ADMIN')")
    List<RentResponse> getAllUserRents(String userId);

    @PreAuthorize("hasAuthority('ADMIN')")
    void returnBook(List<Integer> ids);

}
