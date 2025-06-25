package br.com.caio.repository;

import br.com.caio.model.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Peoplerepository extends JpaRepository<People, Long> {
}
