package dev.abhisek.api.controllers;

import dev.abhisek.api.dto.RentRequest;
import dev.abhisek.api.dto.RentResponse;
import dev.abhisek.api.entities.Role;
import dev.abhisek.api.entities.User;
import dev.abhisek.api.services.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/rent")
public class RentController {
    private final RentService rentService;

    @PostMapping
    public ResponseEntity<Void> rent(@RequestBody List<RentRequest> requests, @AuthenticationPrincipal User user) {
        rentService.rent(requests, user);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<RentResponse>> getRents(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String bookId,
            @RequestParam(required = false) String userId) {
        List<RentResponse> rents;
        if (user.getRole().equals(Role.ADMIN) && bookId != null && !bookId.isEmpty()) {
            rents = rentService.getAllBookRents(bookId);
        } else if (user.getRole().equals(Role.ADMIN) && userId != null && !userId.isEmpty()) {
            rents = rentService.getAllUserRents(userId);
        } else {
            rents = rentService.getAllUserRents(user);
        }
        return ResponseEntity.ok(rents);
    }

    @PostMapping("return")
    public ResponseEntity<Void> returnBook(@RequestBody List<Integer> ids) {
        rentService.returnBook(ids);
        return ResponseEntity.noContent().build();
    }
}
