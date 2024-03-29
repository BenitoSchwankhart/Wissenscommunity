package service;

import model.Role;
import model.User;
import repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository userRepository;



  /**
   * Speichert ein User-Objekt.
   * @param user der zu speichernde User.
   * @return gespeichertes User-Objekt inkl. generierter ID.
   */
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  /**
   * Gibt alle gespeicherten User in einer Liste zurück.
   *
   * @return user-Liste.
   */
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }


  /**
   * Sucht nach einem User mit einem bestimmten Usernamen.
   * @param username der username.
   * @return User-Objekt.
   */
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  /**
   *
   * @param authentication
   * @return aktuellen User
   */
  public User currentUser(Authentication authentication) {
    return userRepository.findByUsername(authentication.getName());
  }

  ///////////////////////////////////////////////////////////////////////////
  // Spring Security Authentication Methoden
  ///////////////////////////////////////////////////////////////////////////

  /**
   * Gibt den aktuell eingeloggten User zurück.
   *
   * @return User.
   */
  /*public User getCurrentUser() {
    return getUserByUsername(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal()).getUsername());
  }*/

  /**
   * Gibt das UserDetails Objekt des aktuell eingeloggten Users zurück.
   * Wird u.U. benötigt um Rollen-Authentifizierungschecks durchzuführen.
   *
   * @return UserDetails Objekt der Spring Security.
   */
  public org.springframework.security.core.userdetails.User getCurrentUserDetails() {
    return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
  }

  /**
   * Überschreibt die Methode, welche für den Login der Spring Security benötigt wird..
   *
   * @param username the username des Benutzers.
   * @return UserDetails Objekt des Spring Security Frameworks.
   * @throws UsernameNotFoundException exception.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("Could not find the user for username " + username);
    }
    List<GrantedAuthority> grantedAuthorities = getUserAuthorities(user.getRoles());
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
        user.isEnabled(), true, true, user.isEnabled(), grantedAuthorities);
  }

  public List<GrantedAuthority> getUserAuthorities(Set<Role> roleSet) {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (Role role : roleSet) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getRolename()));
    }
    return grantedAuthorities;
  }

}
