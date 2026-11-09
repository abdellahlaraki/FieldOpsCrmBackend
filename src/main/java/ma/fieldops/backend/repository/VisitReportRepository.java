package ma.fieldops.backend.repository;

import ma.fieldops.backend.entity.VisitReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitReportRepository extends JpaRepository<VisitReport,Long> {
     Optional<VisitReport> findByVisitId(Long visitId);
}