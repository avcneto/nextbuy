package com.nextbuy.user.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nextbuy.user.dto.user.UserDTO;
import com.nextbuy.user.dto.user.UserUpdateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SequenceGenerator(name = "user_sequence", initialValue = 11)
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  private Long id;

  @Column(unique = true)
  private String cpf;

  private String name;

  private LocalDate birthday;

  private String address;

  private Integer addressNumber;

  @Column(unique = true)
  private String email;

  @JsonIgnore
  private String password;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @JsonIgnore
  @Enumerated(EnumType.STRING)
  private Role role = Role.ADMIN;

  @CreationTimestamp
  private LocalDateTime dateCreated;

  @UpdateTimestamp
  private LocalDateTime updateDate;

  public User(UserDTO userDTO) {
    this.cpf = userDTO.cpf();
    this.name = userDTO.name();
    this.birthday = userDTO.birthday();
    this.gender = userDTO.gender();
    this.email = userDTO.email();
    this.password = userDTO.password();
    this.address = userDTO.address();
    this.addressNumber = userDTO.addressNumber();
  }

  public void update(UserUpdateDTO dto) {
    this.name = ofNullable(dto.name()).orElse(this.name);
    this.cpf = ofNullable(dto.cpf()).orElse(this.cpf);
    this.email = ofNullable(dto.email()).orElse(this.email);
    this.password = ofNullable(dto.password()).orElse(this.password);
    this.birthday = ofNullable(dto.birthday()).orElse(this.birthday);
    this.gender = ofNullable(dto.gender()).orElse(this.gender);
    this.address = ofNullable(dto.address()).orElse(this.address);
    this.addressNumber = ofNullable(dto.addressNumber()).orElse(this.addressNumber);
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  @JsonIgnore
  public String getUsername() {
    return email;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  @JsonIgnore
  public boolean isEnabled() {
    return true;
  }
}
