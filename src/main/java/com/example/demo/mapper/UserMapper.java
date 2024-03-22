package com.example.demo.mapper;

import com.example.demo.controller.request.UserRequest;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapRoleIdsToRoles")
    User toEntity(UserRequest userRequest);

    UserResponse toDto(User user);

    @Named("mapRoleIdsToRoles")
    default Set<Role> mapRoleIdsToRoles(List<Long> roleIds) {
        return roleIds.stream()
                .map(roleId -> {
                    Role role = new Role();
                    role.setId(roleId);
                    return role;
                })
                .collect(Collectors.toSet());
    }

    @Named("mapRolesToRoleIds")
    default List<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }

}
