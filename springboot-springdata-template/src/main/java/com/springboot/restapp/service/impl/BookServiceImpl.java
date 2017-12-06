package com.springboot.restapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.restapp.dao.impl.BookDAOImpl;
import com.springboot.restapp.entity.Book;
import com.springboot.restapp.service.BookService;

@Service
public class BookServiceImpl implements BookService {	
	@Autowired
	private BookDAOImpl bookDAOImpl;

	public List<Book> findAllBooks() {
		List<Book> books = new ArrayList<Book>();
		
		bookDAOImpl.findAll().forEach(books::add);
		return books;
	}

	public Book findById(int id) {
		return bookDAOImpl.findOne(id);
	}

	public Book findByName(String name) {		
		return bookDAOImpl.findByName(name);
	}

	public void saveBook(Book book) {
		bookDAOImpl.save(book);
	}

	public void updateBook(Book book) {
		bookDAOImpl.save(book);
	}

	public void deleteBookById(int id) {
		bookDAOImpl.delete(id);
	}

	public boolean isBookExist(String name) {
		boolean flag = false;
		Book book = bookDAOImpl.findByName(name);
		
		if(book == null) {
			flag= false;			
		}
		
		return flag;
	}

	public void deleteAllBooks() {
		bookDAOImpl.deleteAll();
	}

}
