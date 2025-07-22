package br.com.caio.unittest.mapper.mocks;

import java.util.ArrayList;
import java.util.List;
import br.com.caio.data.dto.BookDTO;
import br.com.caio.model.Book;


public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate("Launch Date Test" + number);
        book.setPrice(20.00 + number);
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate("Launch Date Test" + number);
        book.setPrice(20.00 + number);
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

}