package com.kedop.missingperson.repository;

import com.kedop.missingperson.entity.Human;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {

    Optional<Human> findHumanById(String id);
}
