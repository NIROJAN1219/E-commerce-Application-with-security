package com.example.e_commerce.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // userPrincipal: we store username extracted from JWT (sub or preferred_username)
    @Column(nullable = false)
    private String username;

    @NotNull
    private LocalDate purchaseDate;

    @NotNull
    private String deliveryTime; // "10 AM", "11 AM", "12 PM"

    @NotNull
    private String deliveryLocation; // district

    @NotNull
    private String productName;

    @Min(1)
    private int quantity;

    @Column(length = 2000)
    private String message;
}
