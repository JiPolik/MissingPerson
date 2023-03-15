package com.kedop.missingperson.converters;

import com.kedop.missingperson.entities.Human;
import com.kedop.missingperson.models.importer.HumanImporterModel;
import com.kedop.missingperson.models.HumanModel;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author oleksandrpolishchuk on 31.01.2023
 * @project MissingPerson
 */
@Mapper(componentModel = "spring")
public interface HumanConverter {

    HumanModel humanToHumanModel(Human human);
    Human humanModelToHuman(HumanModel humanModel);
    List<HumanModel> humansToHumanModels(List<Human> humans);
    List<Human> humanModelsToHumans(List<HumanModel> humanModels);
    List<Human> humanImporterModelsToHumans(List<HumanImporterModel> humanImporterModels);
    void updateFromHumanModelToHuman(HumanModel humanModel, @MappingTarget Human human);
}
