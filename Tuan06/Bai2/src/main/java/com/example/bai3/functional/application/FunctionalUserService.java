package com.example.bai3.functional.application;

import com.example.bai3.functional.domain.FunctionalUser;
import com.example.bai3.functional.infrastructure.FunctionalUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FunctionalUserService {
	private final FunctionalUserRepository functionalUserRepository;

	public FunctionalUserService(FunctionalUserRepository functionalUserRepository) {
		this.functionalUserRepository = functionalUserRepository;
	}

	@Transactional
	public FunctionalUser create(String name, String email) {
		return functionalUserRepository.save(new FunctionalUser(name, email, LocalDateTime.now()));
	}

	@Transactional(readOnly = true)
	public Optional<FunctionalUser> getById(Long id) {
		return functionalUserRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<FunctionalUser> listAll() {
		return functionalUserRepository.findAll();
	}
}
