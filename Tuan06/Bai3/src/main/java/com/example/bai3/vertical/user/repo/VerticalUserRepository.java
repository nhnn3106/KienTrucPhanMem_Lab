package com.example.bai3.vertical.user.repo;

import com.example.bai3.vertical.user.domain.VerticalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerticalUserRepository extends JpaRepository<VerticalUser, Long> {
}
