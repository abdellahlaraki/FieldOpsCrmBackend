package ma.fieldops.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.fieldops.backend.enums.VisitOutcome;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "visit_reports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VisitReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitOutcome outcome;
    @Column(length = 2000)
    private String summary;
    private String nextStep ;
    private LocalDate nextActionDate;
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;

    @OneToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "visit_id",nullable = false,unique = true)
    private Visit visit;
}
