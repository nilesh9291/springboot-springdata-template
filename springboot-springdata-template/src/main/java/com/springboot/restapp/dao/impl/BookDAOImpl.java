package com.springboot.restapp.dao.impl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restapp.entity.Book;

public interface BookDAOImpl extends JpaRepository<Book, Integer>{
	public Book findByName(String name);
}
