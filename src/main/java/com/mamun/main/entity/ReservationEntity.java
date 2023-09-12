package com.mamun.main.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    // Many-to-One relationship with Book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    private Date reservationDate;
    private Boolean notificationStatus;

    // Getters and setters

}
