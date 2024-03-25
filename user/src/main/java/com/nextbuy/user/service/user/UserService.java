package com.nextbuy.user.service.user;

import com.nextbuy.user.domain.user.User;
import com.nextbuy.user.dto.user.UserDTO;
import com.nextbuy.user.dto.user.UserUpdateDTO;
import com.nextbuy.user.exception.FailedDependencyException;
import com.nextbuy.user.exception.NotFoundException;
import com.nextbuy.user.exception.ResourceAlreadyExistsException;
import com.nextbuy.user.respository.user.UserRepository;
import com.nextbuy.user.util.Pagination;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

  private static final String CPF_ALREADY_REGISTERED = "CPF already registered";
  private static final String EMAIL_ALREADY_REGISTERED = "Email already registered";
  private static final String USER_NOT_FOUND = "User not found";
  private static final String FAILED_DEPENDENCY_DATABASE = "Error retrieving data from database";
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User registerUser(UserDTO userDTO) {
    var user = repository.findByCpfOrEmail(userDTO.cpf(), userDTO.email());
    validateExistsCpfOrEmail(user, userDTO);

    return repository.save(new User(userDTO));
  }

  public User getUserByCpfOrId(String cpf, Long id) {
    return findUserByCpfOrId(cpf, id);
  }

  public Pagination<User> getAllUser(Integer limit, Integer offset) {
    var pageRequest = PageRequest.of(offset, limit);
    var userPagination = repository.findAll(pageRequest);

    return new Pagination<>(userPagination);
  }

  @Transactional
  public User updateUser(Long id, UserUpdateDTO userDTO) {
    var user = getUserById(id);
    user.update(userDTO);

    return repository.save(user);
  }

  public void deleteUser(Long id) {
    var user = getUserById(id);
    repository.delete(user);
  }

  private User getUserById(Long id) {
    return repository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
  }

  public User findUserByCpfOrId(String cpf, Long id) {
    return findByCpfOrId(cpf, id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
  }

  private Optional<User> findByCpfOrId(String cpf, Long id) {
    try {
      return repository.findByCpfOrId(cpf, id);
    } catch (Exception ex) {
      log.error(FAILED_DEPENDENCY_DATABASE, ex);
      throw new FailedDependencyException(FAILED_DEPENDENCY_DATABASE, ex);
    }
  }

  private void validateExistsCpfOrEmail(Optional<User> user, UserDTO userDTO) {
    user.ifPresent(it -> {
      if (it.getCpf().equals(userDTO.cpf())) {
        throw new ResourceAlreadyExistsException(CPF_ALREADY_REGISTERED);
      }

      if (it.getEmail().equals(userDTO.email())) {
        throw new ResourceAlreadyExistsException(EMAIL_ALREADY_REGISTERED);
      }
    });
  }

}
