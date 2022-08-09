package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Slf4j
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

    @PutMapping("/update")
    public ResponseEntity<Book> update(@RequestBody Book book){
        log.info(book.toString());
        if (book.getId() == null) return ResponseEntity.badRequest().build();
        if (!bookRepository.existsById(book.getId())) return  ResponseEntity.notFound().build();

        log.debug("Registro actualizado");
        return ResponseEntity.ok(bookRepository.save(book));
    }

    /**
     * Elimina libro
     * @param id Identificador
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        log.info("delete", id);
        if (id == null) return ResponseEntity.badRequest().build();
        if (!bookRepository.existsById(id)) return ResponseEntity.notFound().build();

        log.debug("Registro actualizado");
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
