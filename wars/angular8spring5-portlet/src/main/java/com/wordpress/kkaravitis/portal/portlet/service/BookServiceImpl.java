package com.wordpress.kkaravitis.portal.portlet.service;

import book.model.Book;
import book.service.BookLocalService;
import book.service.BookLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.wordpress.kkaravitis.portal.portlet.exception.ApplicationException;
import com.wordpress.kkaravitis.portal.portlet.model.BookDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Override
    public List<BookDTO> getBooks() {
        return BookLocalServiceUtil.getBooks(0, BookLocalServiceUtil.getBooksCount()).stream().map(book -> {
            BookDTO dto = new BookDTO();
            dto.setAuthor(book.getAuthor());
            dto.setTitle(book.getTitle());
            dto.setIsbn(book.getIsbn());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdateBook(BookDTO book) {
        try {
            update(book);
        } catch (PortalException e) {
            save(book);
        }
    }

    @Override
    public void deleteBook(String isbn) throws ApplicationException {
        try {
            BookLocalServiceUtil.deleteBook(isbn);
        } catch(PortalException e) {
            throw new ApplicationException(e);
        }
    }

    private void save(BookDTO book) {
        Book entity = BookLocalServiceUtil.createBook(book.getIsbn());
        BeanUtils.copyProperties(book, entity);
        BookLocalServiceUtil.updateBook(entity);
    }

    private void update(BookDTO book) throws PortalException {
        Book entity = BookLocalServiceUtil.getBook(book.getIsbn());
        BeanUtils.copyProperties(book, entity);
        BookLocalServiceUtil.updateBook(entity);
    }
}
