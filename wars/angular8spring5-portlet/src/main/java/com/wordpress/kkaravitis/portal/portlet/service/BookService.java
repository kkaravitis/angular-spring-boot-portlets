package com.wordpress.kkaravitis.portal.portlet.service;

import com.wordpress.kkaravitis.portal.portlet.exception.ApplicationException;
import com.wordpress.kkaravitis.portal.portlet.model.BookDTO;

import java.util.List;

public interface BookService {
    public List<BookDTO> getBooks();

    public void saveOrUpdateBook(BookDTO bookDTO);

    public void deleteBook(String isbn) throws ApplicationException;
}
