package com.wordpress.kkaravitis.portal.portlet.rest;

import com.wordpress.kkaravitis.portal.portlet.exception.ApplicationException;
import com.wordpress.kkaravitis.portal.portlet.model.BookDTO;
import com.wordpress.kkaravitis.portal.portlet.model.Success;
import com.wordpress.kkaravitis.portal.portlet.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Konstantinos Karavitis
 **/
@RestController
public class BookController {
    @Autowired
    BookService service;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBooks() throws ApplicationException {
        return new ResponseEntity<List<BookDTO>>(service.getBooks(), HttpStatus.OK);
    }

    @PutMapping ("/book/{isbn}")
    public ResponseEntity<Success> updateBook(@RequestBody BookDTO book) throws ApplicationException {
        service.saveOrUpdateBook(book);
        return new ResponseEntity<Success>(new Success("Saved succesfully"), HttpStatus.OK);
    }

    @DeleteMapping("/book/{isbn}")
    public ResponseEntity<Success> deleteBook(@PathVariable(name="isbn") String isbn) throws ApplicationException {
        service.deleteBook(isbn);
        return new ResponseEntity<Success>(new Success("Deleted succesfully"), HttpStatus.ACCEPTED);
    }
}
