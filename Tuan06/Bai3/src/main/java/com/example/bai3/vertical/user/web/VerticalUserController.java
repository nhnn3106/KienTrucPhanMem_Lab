package com.example.bai3.vertical.user.web;

import com.example.bai3.vertical.user.domain.VerticalUser;
import com.example.bai3.vertical.user.service.VerticalUserService;
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
@RequestMapping("/api/vertical/users")
@Validated
public class VerticalUserController {
	private final VerticalUserService verticalUserService;

	public VerticalUserController(VerticalUserService verticalUserService) {
		this.verticalUserService = verticalUserService;
	}

	@PostMapping
	public ResponseEntity<VerticalUser> create(@RequestBody CreateVerticalUserRequest request) {
		VerticalUser created = verticalUserService.create(request.name(), request.email());
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<VerticalUser> getById(@PathVariable Long id) {
		return verticalUserService.getById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<VerticalUser> listAll() {
		return verticalUserService.listAll();
	}

	public record CreateVerticalUserRequest(@NotBlank String name, @NotBlank String email) {
	}
}
