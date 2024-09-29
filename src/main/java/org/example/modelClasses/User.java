package org.example.modelClasses;

/**
 * This is the user model class
 */

public class User {

  private final Long userId;
  private final String username;
  private final String email;

  public User(Long userId, String username, String email) {
    this.userId = userId;
    this.username = username;
    this.email = email;
  }

  public Long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }
}
