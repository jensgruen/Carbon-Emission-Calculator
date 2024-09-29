package org.example.DAOInterfaces;

import java.util.List;
import org.example.modelClasses.UserEmission;

/**
 * This is the UserEmissionDAO interface
 */

public interface UserEmissionDAO {

  List<UserEmission> getAllUserEmissions();
  UserEmission getUserEmission();
  void insertUserEmission ();
  void updateUserEmission ();
  void deleteUserEmission ();
}
