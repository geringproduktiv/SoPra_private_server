package ch.uzh.ifi.hase.soprafs24.service;

import ch.uzh.ifi.hase.soprafs24.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs24.entity.User;
import ch.uzh.ifi.hase.soprafs24.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@WebAppConfiguration
@SpringBootTest
public class UserServiceIntegrationTest {

  @Qualifier("userRepository")
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setup() {
    userRepository.deleteAll();
  }

  // POST success
  @Test
  public void createUser_validInputs_success() {
    // given
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setName("testName");
    testUser.setUsername("testUsername");

    // when
    User createdUser = userService.createUser(testUser);

    // then
    assertEquals(testUser.getId(), createdUser.getId());
    assertEquals(testUser.getName(), createdUser.getName());
    assertEquals(testUser.getUsername(), createdUser.getUsername());
    assertNotNull(createdUser.getToken());
    assertEquals(UserStatus.OFFLINE, createdUser.getStatus());
  }

  // POST failure
  @Test
  public void createUser_duplicateUsername_throwsException() {
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setName("testName");
    testUser.setUsername("testUsername");
    User createdUser = userService.createUser(testUser);

    // attempt to create second user with same username
    User testUser2 = new User();

    // change the name but forget about the username
    testUser2.setName("testName2");
    testUser2.setUsername("testUsername");

    // check that an error is thrown
    assertThrows(ResponseStatusException.class, () -> userService.createUser(testUser2));
  }

  // GET success
  @Test
  public void getUser_validInput_success() {
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setName("testName");
    testUser.setUsername("testUsername");
    User createdUser = userService.createUser(testUser);

    Optional<User> foundUser = userRepository.findById(createdUser.getId());
    if (foundUser.isPresent()) {
      User user = foundUser.get();

      assertTrue(foundUser.isPresent());
      assertEquals(createdUser.getId(), user.getId());
      assertEquals(createdUser.getName(), user.getName());
      assertEquals(createdUser.getUsername(), user.getUsername());
    } else {
      fail("User not found");
    }
  }

  // GET failure
  @Test
  public void getUser_inexistentId_throwsException() {
    assertTrue(userRepository.findById(1L).isEmpty());

    assertThrows(ResponseStatusException.class, () -> userService.getUser(1L));
  }

  // PUT success
  @Test
  public void updateUser_validInput_success() {
    assertNull(userRepository.findByUsername("testUsername"));

    User testUser = new User();
    testUser.setUsername("testUsername");
    User createdUser = userService.createUser(testUser);

    createdUser.setUsername("newUsername");
    User updatedUser = userService.updateUser(createdUser);

    assertEquals(createdUser.getId(), updatedUser.getId());
    assertEquals(createdUser.getUsername(), updatedUser.getUsername());
  }

  // PUT failure
  @Test
  public void updateUser_inexistentId_throwsException() {
    assertTrue(userRepository.findById(1L).isEmpty());

    User testUser = new User();
    testUser.setId(1L);
    testUser.setName("testName");
    testUser.setUsername("testUsername");

    assertThrows(ResponseStatusException.class, () -> userService.updateUser(testUser));
  }
}