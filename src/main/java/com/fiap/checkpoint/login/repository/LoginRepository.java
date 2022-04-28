package com.fiap.checkpoint.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiap.checkpoint.login.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
	
}
