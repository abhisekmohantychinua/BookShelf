package dev.abhisek.api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rent {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date rentedDate;
    private Date returnedDate;

    private Integer quantity;
    private Integer fine;

}
