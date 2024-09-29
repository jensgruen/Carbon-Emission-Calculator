package org.example.DAOInterfaces;


import java.util.List;
import org.example.modelClasses.User;

/**
 * This is the UserDAO interface
 */

public interface UserDAO {

  List<User> getAllUsers();
  User getUser();
  void insertUser ();
  void updateUser ();
  void deleteUser ();



}
