package com.springboot.restapp.service;

import java.util.List;

import com.springboot.restapp.entity.Book;

public interface BookService {

	Book findById(int id);

	Book findByName(String name);

	void saveBook(Book Book);

	void updateBook(Book Book);

	void deleteBookById(int id);

	List<Book> findAllBooks();

	void deleteAllBooks();

	boolean isBookExist(String name);

}
