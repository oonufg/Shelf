package ru.pablo.Spring.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.pablo.Domain.Exceptions.Bookshelf.BookshelfAlreadyExistsException;
import ru.pablo.Domain.Exceptions.Bookshelf.BookshelfAlreadyInSubscribesException;
import ru.pablo.Domain.Exceptions.Bookshelf.BookshelfNotExistException;
import ru.pablo.Domain.Exceptions.Bookshelf.BookshelfNotInSubscribesException;
import ru.pablo.Domain.Exceptions.Shelf.ShelfAlreadyExistException;
import ru.pablo.Domain.Exceptions.Shelf.ShelfNotExistsException;
import ru.pablo.Domain.Exceptions.User.UserNotHaveAccessException;
import ru.pablo.Services.BookshelfService;
import ru.pablo.Services.DTO.BookshelfDTO;
import ru.pablo.Services.DTO.ShelfDTO;

@RestController
@RequestMapping("/bookshelf")
public class BookshelfController {
    private BookshelfService bookshelfService;

    @Autowired
    public BookshelfController(BookshelfService service){
        this.bookshelfService = service;
    }
    @PutMapping()
    public ResponseEntity<?> handleUpdateBookshelf(@RequestHeader("userID") long userId, @RequestBody BookshelfDTO bookshelfDTO){
        try {
            bookshelfService.changeBookshelf(userId, bookshelfDTO);
            return ResponseEntity.ok("");
        }catch (BookshelfNotExistException e){
            return ResponseEntity.notFound().build();
        }catch(UserNotHaveAccessException e){
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(403)).build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> handleGetUserBookShelves(@RequestHeader("userID") long userId){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookshelfService.getBookshelves(userId));
    }


    @PostMapping()
    public ResponseEntity<?> handleCreateBookshelf(@RequestHeader("userID") long userId, @RequestBody BookshelfDTO bookshelfDTO){
        try {
            bookshelfService.createBookshelf(userId, bookshelfDTO);
            return ResponseEntity.ok().body("");
        }catch (BookshelfAlreadyExistsException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> handleDeleteBookshelf(@RequestHeader("userId") long userId, @RequestBody BookshelfDTO bookshelfDTO){
        try {
            bookshelfService.deleteBookshelf(userId, bookshelfDTO.id());
            return ResponseEntity.ok("");
        }catch (UserNotHaveAccessException e){
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(403)).build();
        }catch (BookshelfNotExistException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{bookshelfId}")
    public ResponseEntity<?> handleGetBookshelf(@RequestHeader("userId") long userId, @PathVariable("bookshelfId") long bookshelfId){
        try {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(bookshelfService.getBookshelf(userId, bookshelfId));
        }catch (BookshelfNotExistException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{bookshelfId}")
    public ResponseEntity<?> handleAddShelfToBookshelf(@RequestHeader("userId") long userId, @PathVariable("bookshelfId") long bookshelfId, @RequestBody ShelfDTO shelfDTO){
        try {
            bookshelfService.addShelfToBookshelf(userId, bookshelfId, shelfDTO);
            return ResponseEntity.ok("");
        } catch (BookshelfNotExistException e){
            return ResponseEntity.notFound().build();
        } catch (UserNotHaveAccessException e){
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(403)).build();
        } catch (ShelfAlreadyExistException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{bookshelfId}")
    public ResponseEntity<?> handleDeleteShelfFromBookshelf(@RequestHeader("userId") long userId, @PathVariable("bookshelfId") long bookshelfId, @RequestBody ShelfDTO shelfDTO) {
        try {
            bookshelfService.deleteShelfFromBookshelf(userId, new BookshelfDTO(bookshelfId, null, null), shelfDTO);
            return ResponseEntity.ok("");
        } catch (BookshelfNotExistException | ShelfNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (UserNotHaveAccessException e){
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(403)).build();
        }
    }

    @GetMapping("/s")
    public ResponseEntity<?> handleGetSubscribeanBookshelves(@RequestHeader("userID") long userId){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(bookshelfService.getSubscribeBookshelves(userId));
    }

    @PostMapping("/s")
    public ResponseEntity<?> handleSubscribeToBookshelf(@RequestHeader("userID") long userId, @RequestBody BookshelfDTO bookshelfDTO) {
        try {
            bookshelfService.subscribeToBookshelf(userId, bookshelfDTO);
            return ResponseEntity.ok().build();
        } catch (BookshelfAlreadyInSubscribesException e) {
            return ResponseEntity.badRequest().build();
        } catch (BookshelfNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/s")
    public ResponseEntity<?> handleUnsubscribeFromBookshelf(@RequestHeader("userID") long userId, @RequestBody BookshelfDTO bookshelfDTO) {
        try {
            bookshelfService.unsubscribeFromBookshelf(userId, bookshelfDTO);
            return ResponseEntity.ok().build();
        } catch (BookshelfNotInSubscribesException e) {
            return ResponseEntity.badRequest().build();
        } catch (BookshelfNotExistException e) {
            return ResponseEntity.notFound().build();
        }
    }

}