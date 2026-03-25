package com.example.bai3.functional.infrastructure;

import com.example.bai3.functional.domain.FunctionalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalUserRepository extends JpaRepository<FunctionalUser, Long> {
}
