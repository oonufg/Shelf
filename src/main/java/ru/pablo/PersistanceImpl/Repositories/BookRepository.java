package ru.pablo.PersistanceImpl.Repositories;

import ru.pablo.Domain.Entities.Book;
import ru.pablo.Domain.Exceptions.Book.BookAlreadyOnShelfException;
import ru.pablo.Domain.Exceptions.Book.BookNotExistException;
import ru.pablo.Domain.Persistance.IBookRepository;
import ru.pablo.PersistanceImpl.Mappers.BookMapper;
import ru.pablo.PersistanceImpl.Tables.BookTable;

import java.util.List;

public class BookRepository implements IBookRepository {
    private static BookTable bookTable;

    static{
        bookTable = new BookTable();
    }

    @Override
    public Book getBook(long bookId) throws BookNotExistException{
        if(bookTable.isBookExists(bookId)) {
            return BookMapper.mapBook(bookTable.getBook(bookId));
        }else{
            throw new BookNotExistException();
        }
    }

    @Override
    public List<Book> getBooks(long shelfId) {
        return BookMapper.mapBookList(bookTable.getBooks(shelfId));
    }

    @Override
    public void addBook(long shelfId, Book book) throws BookAlreadyOnShelfException{
        if(!bookTable.isSameBookAlreadyExistsOnShelf(shelfId, book.getTitle())) {
            bookTable.addBook(book.getTitle(), book.getDescription(), shelfId, book.getPayloadId());
        }else{
            throw new BookAlreadyOnShelfException();
        }
    }

    @Override
    public void deleteBook(long shelfId, Book book) throws BookNotExistException{
        if(bookTable.isBookExists(book.getId())){

        }else{
            throw new BookNotExistException();
        }
    }
}
