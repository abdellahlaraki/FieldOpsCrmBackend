package ma.fieldops.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.fieldops.backend.enums.AppStatus;

import java.time.Instant;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Instant scheduledAt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private AppStatus status=AppStatus.PLANNED;

    @Column(length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "assigned_to_id",nullable = false)
    private User assignedTo;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "created_by_id",nullable = false)
    private User createdBy;
}