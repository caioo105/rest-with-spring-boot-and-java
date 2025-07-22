package br.com.caio.repository;

import br.com.caio.model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Peoplerepository extends JpaRepository<People, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE People p SET p.enabled = false WHERE p.id =:id")
    void disabledPeople(@Param("id") Long id);


}
