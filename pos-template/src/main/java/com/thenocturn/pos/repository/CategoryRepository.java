package com.thenocturn.pos.repository;

import java.util.Optional;
import com.thenocturn.pos.entity.Category; // ✅

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Optional<Category> findByName(String name);

}
