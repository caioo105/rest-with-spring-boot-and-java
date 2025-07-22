package br.com.caio.services;

import br.com.caio.controllers.PeopleController;
import br.com.caio.data.dto.PeopleDTO;
import br.com.caio.exception.RequiredObjectIsNullException;
import br.com.caio.exception.ResourceNotFoundException;
import br.com.caio.model.People;
import br.com.caio.repository.Peoplerepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;
import static br.com.caio.mapper.ObjectMapper.parseListObject;
import static br.com.caio.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PeopleService {

    @Autowired
    private Peoplerepository repository;

    private Logger logger = Logger.getLogger(PeopleService.class.getName());

    public List<PeopleDTO> findAll(){
        var people = parseListObject(repository.findAll(), PeopleDTO.class);
        people.forEach(this::addHateoasLinks);
        return people;
    }

    public PeopleDTO findById(Long id){
        logger.info("Finding one Person!");
        var entity =  repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        var dto =  parseObject(entity, PeopleDTO.class);
        addHateoasLinks(dto);
        return dto;

    }


    public PeopleDTO create(PeopleDTO people){
        if (people == null) throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        logger.info("Creating one Person!");
        var entity = parseObject(people, People.class);
        var dto = parseObject(repository.save(entity), PeopleDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

//    public PeopleDTOv2 createV2(PeopleDTOv2 people){
//        logger.info("Creating one Person V2!");
//        var entity = converter.convertDTOToEntity(people);
//        return converter.convertEntityToDTO(repository.save(entity));
//    }


    public PeopleDTO update(PeopleDTO people){
        if (people == null) throw new RequiredObjectIsNullException("It is not allowed to persist a null object!");
        logger.info("Update one Person!");
        People entity = repository.findById(people.getId())//
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        entity.setFirstName(people.getFirstName());
        entity.setLastName(people.getLastName());
        entity.setAddress(people.getAddress());
        entity.setGender(people.getGender());
        var dto = parseObject(repository.save(entity), PeopleDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public PeopleDTO disablePeople(Long id) {
        logger.info("Disabling one Person!");
        repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        repository.disabledPeople(id);
        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PeopleDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Delete one Person!");
        People entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
        repository.delete(entity);
    }

    private void addHateoasLinks(PeopleDTO dto) {
        dto.add(linkTo(methodOn(PeopleController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PeopleController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(PeopleController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(PeopleController.class).update(dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PeopleController.class).disablePeople(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(PeopleController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
