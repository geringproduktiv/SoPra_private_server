package ch.uzh.ifi.hase.soprafs24.rest.mapper;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPutDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */
@Mapper
public interface DTOMapper {

  DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

  // UserPostDTO <-> User
  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password") 
  @Mapping(source = "birthday", target = "birthday")
  @Mapping(source = "creation_date", target = "creation_date")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "token", ignore = true)
  User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

  // User <-> UserPostDTO
  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "birthday", target = "birthday")
  @Mapping(source = "creation_date", target = "creation_date")
  @Mapping(source = "id", target = "id")
  @Mapping(source = "status", target = "status")
  @Mapping(source = "token", target = "token")
  UserPostDTO convertEntityToUserPostDTO(User user);

 // User <-> UserGetDTO
  @Mapping(source = "id", target = "id")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "password", target = "password")
  @Mapping(source = "creation_date", target = "creation_date")
  @Mapping(source = "birthday", target = "birthday")
  @Mapping(source = "token", target = "token")
  @Mapping(source = "status", target = "status")
  UserGetDTO convertEntityToUserGetDTO(User user);

  // UserPutDTO <-> User
  @Mapping(source = "username", target = "username")
  @Mapping(source = "birthday", target = "birthday")
  @Mapping(source = "name", target = "name")
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "creation_date", ignore = true)
  @Mapping(source = "id", target = "id")
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "token", ignore = true)
  User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);

  // User <-> UserPutDTO
  @Mapping(source = "name", target = "name")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "birthday", target = "birthday")
  @Mapping(source = "status", target = "status")
  UserPutDTO convertEntityToUserPutDTO(User user);
}
