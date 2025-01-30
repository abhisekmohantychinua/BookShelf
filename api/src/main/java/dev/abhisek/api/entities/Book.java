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
public class Book {
    @Id
    private String id;
    @Column(unique = true)
    private String title;
    private String author;
    @Column(columnDefinition = "TEXT", length = 500)
    private String description;
    private Integer price;
    private Integer quantity;
    private Boolean featured;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Rent> rents;
    private String image;
}
