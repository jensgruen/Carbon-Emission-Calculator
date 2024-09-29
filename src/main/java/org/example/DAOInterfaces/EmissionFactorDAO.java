package org.example.DAOInterfaces;

import java.util.List;
import org.example.modelClasses.EmissionFactor;

/**
 * This is the EmissionFactorDAO interface
 */

public interface EmissionFactorDAO {

  List<EmissionFactor> getAllEmissionFactors();
  EmissionFactor getEmissionFactor();
  void createEmissionFactor ();
  void updateEmissionFactor ();
  void deleteEmissionFactor ();

}
