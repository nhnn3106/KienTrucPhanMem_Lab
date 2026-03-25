package com.example.bai3.vertical.user.service;

import com.example.bai3.vertical.user.domain.VerticalUser;
import com.example.bai3.vertical.user.repo.VerticalUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerticalUserService {
	private final VerticalUserRepository verticalUserRepository;

	public VerticalUserService(VerticalUserRepository verticalUserRepository) {
		this.verticalUserRepository = verticalUserRepository;
	}

	@Transactional
	public VerticalUser create(String name, String email) {
		return verticalUserRepository.save(new VerticalUser(name, email, LocalDateTime.now()));
	}

	@Transactional(readOnly = true)
	public Optional<VerticalUser> getById(Long id) {
		return verticalUserRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<VerticalUser> listAll() {
		return verticalUserRepository.findAll();
	}
}
