package com.example.demo.mapper;

import com.example.demo.controller.request.ProgramRequest;
import com.example.demo.controller.response.ProgramResponse;
import com.example.demo.entity.Program;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProgramMapper {

    @Mapping(target = "parent", source = "parentId", qualifiedByName = "mapParentIdToParent")
    Program toEntity(ProgramRequest goodDto);

    @Mapping(target = "parentId", source = "parent", qualifiedByName = "mapParentToParentId")
    ProgramResponse toDto(Program good);

    @Named("mapParentIdToParent")
    default Program mapParentIdToParent(Long parentId) {
        Program program = new Program();
        program.setId(parentId);
        return program;
    }

    @Named("mapParentToParentId")
    default Long mapParentToParentId(Program program) {
        return program.getId();
    }

}
