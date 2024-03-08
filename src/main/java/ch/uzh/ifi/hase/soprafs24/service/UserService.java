package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import ch.uzh.ifi.hase.soprafs24.rest.dto.UserPostDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to
 * the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;

  @Autowired
  public UserService(@Qualifier("userRepository") UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> getUsers() {
    return this.userRepository.findAll();
  }

  public Optional<User> getUser(Long userId) {
    return this.userRepository.findById(userId);
  }

  public User createUser(User newUser) {

    // Set the token for the user
    newUser.setToken(UUID.randomUUID().toString());

    // Set the status for the user
    newUser.setStatus(UserStatus.OFFLINE);

    // check if the user already exists in the database
    checkIfUserExists(newUser);

    // Save the user to the database
    newUser = userRepository.save(newUser);

    // flush() is used to write the changes to the database
    userRepository.flush();

    log.debug("Created Information for User: {}", newUser);

    return newUser;
    
  }

  /**
   * This is a helper method that will check the uniqueness criteria of the
   * username and the name
   * defined in the User entity. The method will do nothing if the input is unique
   * and throw an error otherwise.
   *
   * @param userToBeCreated
   * @throws org.springframework.web.server.ResponseStatusException
   * @see User
   */
  private void checkIfUserExists(User userToBeCreated) {
    User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
    User userByName = userRepository.findByName(userToBeCreated.getName());

    String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
    if (userByUsername != null && userByName != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          String.format(baseErrorMessage, "username and the name", "are"));
    } else if (userByUsername != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "username", "is"));
    } else if (userByName != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(baseErrorMessage, "name", "is"));
    }
  }

  // Validate the username and password.
  // Returns: If user exist and password is correct. 
  public User validateUser(UserPostDTO user) {

    // Get the user information from teh DB by user name
    User userByUsername = userRepository.findByUsername(user.getUsername());

    //If user not found, return false
    if (userByUsername == null) return null;

    // Validate if the user 
    if (userByUsername.getPassword().equals(user.getPassword()) ) {
      return userByUsername;
    }

    return null;

  }

  public User updateUser(User user) {
    Optional<User> existingUser = userRepository.findById(user.getId());

    if (existingUser.isPresent()) {
      User userToUpdate = existingUser.get();
      userToUpdate.setUsername(user.getUsername());
      userToUpdate.setBirthday(user.getBirthday());
      System.out.println("User to update: " + userToUpdate.getId());
      System.out.println("User to update: " + userToUpdate.getUsername());
      System.out.println("User to update: " + userToUpdate.getBirthday());

      // Update fields of userToUpdate with values from user
      // For example, if User has a name field that can be updated:
      // userToUpdate.setName(user.getName());

      return userRepository.save(userToUpdate);
    } else {
      // Handle case where user doesn't exist in the database
      // You could throw an exception, return null, or return a default User object
      return null;
    }
  }
}
