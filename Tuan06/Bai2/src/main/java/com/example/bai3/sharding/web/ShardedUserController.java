package com.example.bai3.sharding.web;

import com.example.bai3.sharding.domain.Gender;
import com.example.bai3.sharding.service.ShardedUserService;
import com.example.bai3.sharding.service.ShardedUserService.ShardedUserView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/api/sharded/users")
@Validated
public class ShardedUserController {
	private final ShardedUserService shardedUserService;

	public ShardedUserController(ShardedUserService shardedUserService) {
		this.shardedUserService = shardedUserService;
	}

	@PostMapping
	public ResponseEntity<ShardedUserView> create(@RequestBody CreateShardedUserRequest request) {
		ShardedUserView created = shardedUserService.createUser(request.name(), request.email(), request.gender());
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{gender}/{id}")
	public ResponseEntity<ShardedUserView> getById(@PathVariable Gender gender, @PathVariable Long id) {
		return shardedUserService.getById(gender, id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public List<ShardedUserView> listAll() {
		return shardedUserService.listAll();
	}

	public record CreateShardedUserRequest(
			@NotBlank String name,
			@NotBlank String email,
			@NotNull Gender gender
	) {
	}
}
