package org.example.DAOInterfaces;

import java.util.List;
import org.example.modelClasses.EmissionGoal;

/**
 * This is the EmissionGoalDAO interface
 */

public interface EmissionGoalDAO {

  List<EmissionGoal> getEmissionGoals();
  EmissionGoal getEmissionGoal();
  void createEmissionGoal ();
  void updateEmissionGoal ();
  void deleteEmissionGoal ();

}
