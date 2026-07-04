package com.bill.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.entities.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(String email);
	
}