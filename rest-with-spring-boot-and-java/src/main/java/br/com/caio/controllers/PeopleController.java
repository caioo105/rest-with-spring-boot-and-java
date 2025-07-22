package br.com.caio.controllers;

import br.com.caio.data.dto.PeopleDTO;
import br.com.caio.services.PeopleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PeopleController implements br.com.caio.controllers.docs.PeopleControllerDocs {

    @Autowired
    private PeopleService service;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public List<PeopleDTO> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public PeopleDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public PeopleDTO create(@RequestBody PeopleDTO people){
        return service.create(people);
    }

//    @PostMapping(value = "/v2",
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public PeopleDTOv2 createV2(@RequestBody PeopleDTOv2 people){
//        return service.createV2(people);
//    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public PeopleDTO update(@RequestBody PeopleDTO people){
        return service.update(people);
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})

    @Override
    public PeopleDTO disablePeople(@PathVariable("id") Long id) {
       return service.disablePeople(id);
    }

    @DeleteMapping(value = "/{id}")

    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
