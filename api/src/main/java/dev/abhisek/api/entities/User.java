package dev.abhisek.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_users")
public class User {
    @Id
    private String id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Rent> rents;
}
