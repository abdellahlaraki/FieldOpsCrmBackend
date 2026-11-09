package ma.fieldops.backend.repository;

import ma.fieldops.backend.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer> findByName(String name);

    Page<Customer> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<Customer> findByCityIgnoreCase(String city,Pageable pageable);
}
