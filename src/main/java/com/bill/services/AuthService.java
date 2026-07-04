package com.bill.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bill.dto.AuthResponse;
import com.bill.entities.User;
import com.bill.repo.UserRepository;
import com.bill.security.JwtUtil;

@Service
public class AuthService
{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	// Register - hashes the password before saving, never store plain text
	public User register(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		if (user.getRole() == null || user.getRole().isBlank())
		{
			// Default new signups to CASHIER; promote to ADMIN manually in the DB if needed
			user.setRole("CASHIER");
		}

		return userRepository.save(user);
	}

	// Login - verifies hashed password and issues a JWT on success
	public AuthResponse login(User loginUser)
	{
		Optional<User> userOpt = userRepository.findByEmail(loginUser.getEmail());

		if (userOpt.isEmpty())
		{
			return null;
		}

		User dbUser = userOpt.get();

		if (!passwordEncoder.matches(loginUser.getPassword(), dbUser.getPassword()))
		{
			return null;
		}

		String token = jwtUtil.generateToken(dbUser.getEmail(), dbUser.getName(), dbUser.getRole());

		return new AuthResponse(token, dbUser.getId(), dbUser.getName(), dbUser.getEmail(), dbUser.getRole());
	}
}
