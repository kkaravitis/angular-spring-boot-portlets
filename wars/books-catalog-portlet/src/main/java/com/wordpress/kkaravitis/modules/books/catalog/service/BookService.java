package com.wordpress.kkaravitis.modules.books.catalog.service;

import com.wordpress.kkaravitis.modules.books.catalog.exception.ApplicationException;
import com.wordpress.kkaravitis.modules.books.catalog.model.BookDTO;

import java.util.List;

/**
 * @author Konstantinos Karavitis
 **/
public interface BookService {
    public List<BookDTO> getBooks();

    public void saveOrUpdateBook(BookDTO bookDTO);

    public void deleteBook(String isbn) throws ApplicationException;
}
