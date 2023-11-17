package com.example.WatchShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @Column(columnDefinition = "nvarchar(250)")
  private String fullName;
  private Date birthDate;
  @Column(columnDefinition = "nvarchar(250)")
  private String address;
  @JsonIgnore
  private String password;
  private String email;
  private String phone;
  @Column(nullable = true)
  private Boolean isDeleted = false;

  @ManyToOne
  @JoinColumn(name = "idRole")
  private Roles roles;

  @OneToMany(mappedBy = "users")
  private List<Orders> orders; // to list

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(roles.getName()));
    return authorities;
  }

  @Override

  public String getUsername() {
    return getFullName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
