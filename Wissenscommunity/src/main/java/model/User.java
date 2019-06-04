package model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue
  private Integer id;

  @Column
  private String username;

  @Column
  private String email;

  @Column
  private String password;

  @Column
  private  String geburtsdatum;

  @Column
  private  String fachbereich;


  public User() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getGeburtsdatum() {
    return geburtsdatum;
  }

  public void setGeburtsdatum(String geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
  }

  public String getFachbereich() {
    return fachbereich;
  }

  public void setFachbereich(String fachbereich) {
    this.fachbereich = fachbereich;
  }
}
