package com.springboot.restapp.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restapp.entity.Book;
import com.springboot.restapp.service.BookService;
import com.springboot.restapp.util.CustomErrorType;

@RestController
@RequestMapping("/book/")
public class BookController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BookService bookService;

	// -------------------Create a Book-------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Book book) {
		if (bookService.isBookExist(book.getName())) {
			logger.error("A Book with name {} already exist", book.getName());
			 return new ResponseEntity(new CustomErrorType("A Book with name " + book.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		bookService.saveBook(book);

		return new ResponseEntity<Book>(book, HttpStatus.CREATED);
	}

	// ------------------- Update a Book
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Book book) {
		Book currentBook = bookService.findById(id);

		if (currentBook == null) {
			logger.error("Unable to update. Book with id {} not found.", id);
			return new ResponseEntity<Object>(new CustomErrorType("Book with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}

		currentBook.setName(book.getName());
		currentBook.setDescription(book.getDescription());
		
		bookService.updateBook(currentBook);
		return new ResponseEntity<Book>(currentBook, HttpStatus.OK);
	}

	// -------------------Retrieve Single Book ------------------------------------------
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable("id") int id) {
		Book book = bookService.findById(id);
		if (book == null) {
			logger.error("Book with id {} not found.", id);
			return new ResponseEntity<Object>(new CustomErrorType("Book with id " + id + " not found"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> getAll() {
		List<Book> books = bookService.findAllBooks();
		if (books.isEmpty()) {
			return new ResponseEntity(new CustomErrorType("No records found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	// ------------------- Delete a Book -----------------------------------------
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		Book book = bookService.findById(id);
		if (book == null) {
			logger.error("Unable to delete. Book with id {} not found.", id);
			return new ResponseEntity<Object>(new CustomErrorType("Book with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}
		bookService.deleteBookById(id);
		return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Books-----------------------------
	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
	public ResponseEntity<Book> deleteAll() {
		bookService.deleteAllBooks();
		return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
	}

}