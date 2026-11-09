package ma.fieldops.backend.repository;

import ma.fieldops.backend.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit,Long> {

    Optional<Visit> findByAppointment(Long appointmentId);

    boolean existsAllByAppointmentId(Long appointmentId);
}
