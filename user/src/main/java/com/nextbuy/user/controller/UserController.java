package com.nextbuy.user.controller;


import com.nextbuy.user.domain.user.User;
import com.nextbuy.user.dto.user.UserDTO;
import com.nextbuy.user.dto.user.UserUpdateDTO;
import com.nextbuy.user.service.user.UserService;
import com.nextbuy.user.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/user")
public record UserController(
        UserService service
) implements Controller {

  private static final String USER_ID_PATH = "/user/{id}";
  private static final String CPF = "cpf";
  private static final String ALL = "all";
  private static final String ID = "id";
  private static final String USER_HAS_BEEN_DELETED = "User has been deleted ";
  private static final String TEN = "10";
  private static final String ZERO = "0";

  @PostMapping(headers = X_API_VERSION_1)
  public ResponseEntity<User> registerUser(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) {
    var user = service.registerUser(userDTO);

    return ResponseEntity
            .created(uriBuilder.path(USER_ID_PATH).buildAndExpand(user.getId()).toUri())
            .body(user);
  }

  @GetMapping(headers = X_API_VERSION_1, params = {CPF})
  public ResponseEntity<User> getUserByCpf(String cpf) {
    return ResponseEntity.ok(service.getUserByCpf(cpf));
  }

  @GetMapping(headers = X_API_VERSION_1, path = ALL)
  public ResponseEntity<Pagination<User>> getAllUser(
          @RequestParam(defaultValue = TEN) Integer limit,
          @RequestParam(defaultValue = ZERO) Integer offset
  ) {
    var user = service.getAllUser(limit, offset);

    return ResponseEntity.ok(user);
  }

  @PutMapping(headers = X_API_VERSION_1, params = {ID})
  public ResponseEntity<User> updateUser(@RequestBody @Valid UserUpdateDTO userDTO, Long id) {
    var user = service.updateUser(id, userDTO);

    return ResponseEntity.ok(user);
  }

  @DeleteMapping(headers = X_API_VERSION_1, params = {ID})
  public ResponseEntity<String> deleteUser(Long id) {
    service.deleteUser(id);

    return ResponseEntity.ok(USER_HAS_BEEN_DELETED);
  }

}
