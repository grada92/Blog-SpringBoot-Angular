package io.danielegradassai.repository;

import io.danielegradassai.entity.ValidationAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidationAdminRepository extends JpaRepository<ValidationAdmin, Long> {
}
