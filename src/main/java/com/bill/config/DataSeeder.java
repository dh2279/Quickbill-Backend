package com.bill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bill.entities.User;
import com.bill.repo.UserRepository;

/**
 * Runs once on every application startup. If no ADMIN user exists yet, creates
 * a default one so you never get locked out after a fresh DB / DB drop.
 *
 * IMPORTANT: change the default password immediately after first login, and
 * consider removing/disabling this seeder in production once you have a real
 * admin account set up.
 */
@Component
public class DataSeeder implements CommandLineRunner
{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${admin.default.email:admin@quickbill.com}")
	private String defaultAdminEmail;

	@Value("${admin.default.password:Admin@123}")
	private String defaultAdminPassword;

	@Override
	public void run(String... args)
	{
		boolean adminExists = userRepository.findAll().stream().anyMatch(u -> "ADMIN".equalsIgnoreCase(u.getRole()));

		if (adminExists)
		{
			return;
		}

		User admin = new User();
		admin.setName("Admin");
		admin.setEmail(defaultAdminEmail);
		admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
		admin.setRole("ADMIN");

		userRepository.save(admin);

		System.out.println("=====================================================");
		System.out.println(" No ADMIN user found. Default admin created:");
		System.out.println(" Email    : " + defaultAdminEmail);
		System.out.println(" Password : " + defaultAdminPassword);
		System.out.println(" >>> Please log in and change this password. <<<");
		System.out.println("=====================================================");
	}
}