package com.bill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bill.dto.AuthResponse;
import com.bill.entities.User;
import com.bill.services.AuthService;

@RestController
@RequestMapping("/auth/")
@CrossOrigin("http://localhost:5173")
public class AuthController
{

	@Autowired
	private AuthService authService;

	// Register
	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody User user)
	{
		User saved = authService.register(user);

		// Never send the (hashed) password back to the client
		saved.setPassword(null);

		return ResponseEntity.ok(saved);
	}

	// Login - returns a JWT token on success
	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody User loginUser)
	{
		AuthResponse response = authService.login(loginUser);

		if (response == null)
		{
			return ResponseEntity.status(401).body("Invalid email or password");
		}

		return ResponseEntity.ok(response);
	}

}
