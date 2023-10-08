package ru.pablo.PersistanceImpl.Repositories;

import ru.pablo.Domain.Entities.Bookshelf;
import ru.pablo.Domain.Entities.UserBookshelf;
import ru.pablo.Domain.Persistance.IBookshelfRepository;
import ru.pablo.PersistanceImpl.Mappers.BookshelfMapper;
import ru.pablo.PersistanceImpl.Repositories.Tables.BookshelfTable;
import ru.pablo.PersistanceImpl.Repositories.Tables.UsersBookshelvesTable;

import java.util.List;

public class BookshelfRepository implements IBookshelfRepository {
    private static BookshelfTable bookshelfTable;
    private static UsersBookshelvesTable usersBookshelvesTable;

    static {
        bookshelfTable = new BookshelfTable();
        usersBookshelvesTable = new UsersBookshelvesTable();
    }

    @Override
    public Bookshelf getBookshelf(long bookshelfID) {
        return null;
    }

    @Override
    public List<Bookshelf> getBookshelves(long userId) {
        return BookshelfMapper.mapListBookshelf(usersBookshelvesTable.getUserBookshelves(userId));
    }

    @Override
    public void appendBookshelf(long userID, Bookshelf bookshelfToAdd) {
        if(bookshelfToAdd instanceof UserBookshelf){
            UserBookshelf currentBookshelf = (UserBookshelf)bookshelfToAdd;
            usersBookshelvesTable.subscribeToBookshelf(userID, currentBookshelf.getId(),false);
        }else if(bookshelfToAdd instanceof Bookshelf){
            long bookshelfId = bookshelfTable.createBookshelf(bookshelfToAdd.getTitle());
            usersBookshelvesTable.subscribeToBookshelf(userID, bookshelfId, true);
        }
    }
}