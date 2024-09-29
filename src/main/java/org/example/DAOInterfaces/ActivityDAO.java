package org.example.DAOInterfaces;

import java.util.List;
import org.example.modelClasses.Activity;

/**
 * This is the ActivityDAO interface
 */

public interface ActivityDAO {

  List<Activity> getAllActivities();
  Activity getActivity();
  void insertActivity ();
  void updateActivity ();
  void deleteActivity ();

}

