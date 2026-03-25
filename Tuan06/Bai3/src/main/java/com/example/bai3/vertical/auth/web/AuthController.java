package com.example.bai3.vertical.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vertical/auth")
public class AuthController {
	@PostMapping("/login")
	public ResponseEntity<String> login() {
		return ResponseEntity.ok("login-ok");
	}
}
