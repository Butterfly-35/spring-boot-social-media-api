package com.sha.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sha.insta.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
