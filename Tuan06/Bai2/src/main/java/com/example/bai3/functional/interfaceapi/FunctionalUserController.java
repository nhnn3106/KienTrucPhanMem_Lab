package com.example.bai3.functional.interfaceapi;

import com.example.bai3.functional.application.FunctionalUserService;
import com.example.bai3.functional.domain.FunctionalUser;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/functional/users")
@Validated
public class FunctionalUserController {
	private final FunctionalUserService functionalUserService;

	public FunctionalUserController(FunctionalUserService functionalUserService) {
		this.functionalUserService = functionalUserService;
	}

	@PostMapping
	public ResponseEntity<FunctionalUser> create(@RequestBody CreateFunctionalUserRequest request) {
		FunctionalUser created = functionalUserService.create(request.name(), request.email());
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FunctionalUser> getById(@PathVariable Long id) {
		return functionalUserService.getById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<FunctionalUser> listAll() {
		return functionalUserService.listAll();
	}

	public record CreateFunctionalUserRequest(@NotBlank String name, @NotBlank String email) {
	}
}
