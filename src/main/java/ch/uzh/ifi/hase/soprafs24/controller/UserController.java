package ch.uzh.ifi.hase.soprafs24.controller;

import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs24.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs24.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User Controller
 * This class is responsible for handling all REST request that are related to
 * the user.
 * The controller will receive the request and delegate the execution to the
 * UserService and finally return the result.
 */
@RestController
public class UserController {

  private final UserService userService;

  UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<UserGetDTO> getAllUsers() {
    // fetch all users in the internal representation
    List<User> users = userService.getUsers();
    List<UserGetDTO> userGetDTOs = new ArrayList<>();

    // convert each user to the API representation
    for (User user : users) {
      userGetDTOs.add(DTOMapper.INSTANCE.convertEntityToUserGetDTO(user));
    }
    return userGetDTOs;
  }

  @GetMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserGetDTO getUser(@PathVariable Long userId) {

    // fetch the user in the internal representation
    Optional<User> user = userService.getUser(userId);

    // convert user to the API representation
    return DTOMapper.INSTANCE.convertEntityToUserGetDTO(user.get());
  }

  @PutMapping("/users/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserPutDTO updateUser(@RequestBody UserPutDTO userPutDTO) {
    System.out.println("User to update: " + userPutDTO.getBirthday());
    System.out.println("User to update: " + userPutDTO.getId());
    // convert API user to internal representation
    User userInput = DTOMapper.INSTANCE.convertUserPutDTOtoEntity(userPutDTO);

    // update user
    User updatedUser = userService.updateUser(userInput);

    return DTOMapper.INSTANCE.convertEntityToUserPutDTO(updatedUser);
  }

  @PostMapping("/user-validation")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserPostDTO validateUser(@RequestBody UserPostDTO userPostDTO) {

    // fetch all users in the internal representation
    User user = userService.validateUser(userPostDTO);

    return DTOMapper.INSTANCE.convertEntityToUserPostDTO(user);
    
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public UserPostDTO createUser(@RequestBody UserPostDTO userPostDTO) {

    // convert API user to internal representation
    User userInput = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(userPostDTO);

    // create user
    User createdUser = userService.createUser(userInput);

    // convert internal representation of user back to API
    return DTOMapper.INSTANCE.convertEntityToUserPostDTO(createdUser);
  }
}
