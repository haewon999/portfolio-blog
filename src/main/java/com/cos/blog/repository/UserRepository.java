package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
//자동으로 bean 등록이 된다.
//Integer PK이여서 사용
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);
}
//User findByUsernameAndPassword(String username, String password);