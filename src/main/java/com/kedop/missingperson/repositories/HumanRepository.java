package com.kedop.missingperson.repositories;

import com.kedop.missingperson.entities.Human;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Repository
public interface HumanRepository extends JpaRepository<Human, Long>,
        JpaSpecificationExecutor<Human> {

    Optional<Human> findHumanById(Long id);
}
