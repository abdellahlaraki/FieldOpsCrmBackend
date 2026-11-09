package ma.fieldops.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "visits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Visit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Instant startedAt;
    private Instant endedAt;
    private Double startLatitude;
    private Double startLongitude;
    private  Float startAccuracyM;
    private Instant locationAt;
    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "appointment_id",nullable = false,unique = true)
    private Appointment appointment;
}