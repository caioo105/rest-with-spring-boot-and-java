//package br.com.caio.mapper.custom;
//
//import br.com.caio.data.dto.V2.PeopleDTOv2;
//import br.com.caio.model.People;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class PeopleMapper {
//
//    public PeopleDTOv2 convertEntityToDTO(People people){
//        PeopleDTOv2 dto = new PeopleDTOv2();
//        dto.setId(people.getId());
//        dto.setFirstName(people.getFirstName());
//        dto.setLastName(people.getLastName());
//        dto.setBirthDate(new Date());
//        dto.setAddress(people.getAddress());
//        dto.setGender(people.getGender());
//        return dto;
//    }
//
//    public People convertDTOToEntity(PeopleDTOv2 people){
//        People entity = new People();
//        entity.setId(people.getId());
//        entity.setFirstName(people.getFirstName());
//        entity.setLastName(people.getLastName());
//        //entity.setBirthDate(new Date());
//        entity.setAddress(people.getAddress());
//        entity.setGender(people.getGender());
//        return entity;
//    }
//}
