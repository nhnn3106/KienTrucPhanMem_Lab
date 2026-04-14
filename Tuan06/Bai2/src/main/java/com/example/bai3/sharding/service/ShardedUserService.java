package com.example.bai3.sharding.service;

import com.example.bai3.sharding.domain.Gender;
import com.example.bai3.sharding.domain.ShardUser01;
import com.example.bai3.sharding.domain.ShardUser02;
import com.example.bai3.sharding.repo.ShardUser01Repository;
import com.example.bai3.sharding.repo.ShardUser02Repository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShardedUserService {
	private final ShardUser01Repository shardUser01Repository;
	private final ShardUser02Repository shardUser02Repository;

	public ShardedUserService(ShardUser01Repository shardUser01Repository, ShardUser02Repository shardUser02Repository) {
		this.shardUser01Repository = shardUser01Repository;
		this.shardUser02Repository = shardUser02Repository;
	}

	@Transactional
	public ShardedUserView createUser(String name, String email, Gender gender) {
		LocalDateTime now = LocalDateTime.now();
		if (gender == Gender.MALE) {
			ShardUser01 saved = shardUser01Repository.save(new ShardUser01(name, email, now));
			return ShardedUserView.from(saved, gender);
		}
		ShardUser02 saved = shardUser02Repository.save(new ShardUser02(name, email, now));
		return ShardedUserView.from(saved, gender);
	}

	@Transactional(readOnly = true)
	public Optional<ShardedUserView> getById(Gender gender, Long id) {
		if (gender == Gender.MALE) {
			return shardUser01Repository.findById(id).map(user -> ShardedUserView.from(user, gender));
		}
		return shardUser02Repository.findById(id).map(user -> ShardedUserView.from(user, gender));
	}

	@Transactional(readOnly = true)
	public List<ShardedUserView> listAll() {
		List<ShardedUserView> result = new ArrayList<>();
		shardUser01Repository.findAll().forEach(user -> result.add(ShardedUserView.from(user, Gender.MALE)));
		shardUser02Repository.findAll().forEach(user -> result.add(ShardedUserView.from(user, Gender.FEMALE)));
		return result;
	}

	public record ShardedUserView(Long id, String name, String email, Gender gender, LocalDateTime createdAt) {
		public static ShardedUserView from(ShardUser01 user, Gender gender) {
			return new ShardedUserView(user.getId(), user.getName(), user.getEmail(), gender, user.getCreatedAt());
		}

		public static ShardedUserView from(ShardUser02 user, Gender gender) {
			return new ShardedUserView(user.getId(), user.getName(), user.getEmail(), gender, user.getCreatedAt());
		}
	}
}
