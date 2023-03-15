package com.kedop.missingperson.repositories;

import static java.util.Optional.ofNullable;

import com.kedop.missingperson.domain.FilterCriteria;
import com.kedop.missingperson.entities.Human;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author oleksandrpolishchuk on 08.03.2023
 * @project MissingPerson
 */
@UtilityClass
public class Specifications {

    public Specification<Human> criteriaToSpec(FilterCriteria filterCriteria) {
        return Specification.where(nullableInSpec(filterCriteria.getFirstNameEng(), "property"));
    }

    private Specification<Human> nullableInSpec(List<String> elements, String property) {
        return ofNullable(elements).map(list -> (Specification<Human>) (r, q, cb) ->
                r.get(property)
                        .in(list))
                .orElse(null);
    }
}
