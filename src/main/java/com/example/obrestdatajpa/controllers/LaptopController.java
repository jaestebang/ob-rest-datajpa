package com.example.obrestdatajpa.controllers;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.entities.Laptop;
import com.example.obrestdatajpa.repositories.LaptopRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {

    private LaptopRepository laptopRepository;

    /**
     * Constructor de la clase
     * @param laptopRepository Se inyecta automáticamente
     */
    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    /**
     * Obtiene todos los libros
     * @return Lista de libros
     */
    @GetMapping("/find")
    public List<Laptop> findAll(){
        return laptopRepository.findAll();
    }

    /**
     * Obtiene libro por ID
     * @param id
     * @return Book
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<Laptop> findById(@PathVariable Long id){
        Optional<Laptop> optionalLaptop = laptopRepository.findById(id);
        return optionalLaptop.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    /**
     * Crear nuevo Laptop
     * @param laptop Laptop
     * @return laptop
     */
    @PostMapping("/create")
    public Laptop create(@RequestBody Laptop laptop){
        return laptopRepository.save(laptop);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id){
        Optional<Laptop> optionalLaptop = laptopRepository.findById(id);
        if (optionalLaptop.isPresent()){
            laptopRepository.delete(optionalLaptop.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
