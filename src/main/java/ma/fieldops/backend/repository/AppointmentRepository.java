package ma.fieldops.backend.repository;

import ma.fieldops.backend.entity.Appointment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    // agenda d'un commercial sur une période (avec le client chargé au meme temps)
    @EntityGraph(attributePaths = "customer")
    List<Appointment> findByAssignedToIdAndScheduledAtBetweenOrderByScheduledAtAsc(Long userId, Instant from ,Instant to);

    Optional<Appointment> findByIdAndAssignedToId(Long id,Long userId);

    //historiqye
    @EntityGraph(attributePaths = "customer")
    List<Appointment> findByCustomerIdOrderByScheduledAtDesc(Long userId);
}