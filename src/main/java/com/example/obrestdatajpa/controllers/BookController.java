package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookRepository bookRepository;

    /**
     * Constructor de la clase
     * @param bookRepository Se inyecta automáticamente
     */
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Obtiene todos los libros
     * @return Lista de libros
     */
    @GetMapping("/find")
    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    /**
     * Obtiene libro por ID
     * @param id
     * @return Book
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Crear nuevo libro
     * @param book Libro
     * @return book
     */
    @PostMapping("/create")
    public Book create(@RequestBody Book book){
        return bookRepository.save(book);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()){
            bookRepository.delete(optionalBook.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
