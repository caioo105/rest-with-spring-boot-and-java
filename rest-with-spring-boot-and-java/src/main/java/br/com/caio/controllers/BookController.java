package br.com.caio.controllers;

import br.com.caio.data.dto.BookDTO;
import br.com.caio.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// @CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/book")
@Tag(name = "Book", description = "Endpoints for Managing Book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    public List<BookDTO> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    public BookDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    public BookDTO create(@RequestBody BookDTO book){
        return service.create(book);
    }

    @DeleteMapping(value = "/{id}")

    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
