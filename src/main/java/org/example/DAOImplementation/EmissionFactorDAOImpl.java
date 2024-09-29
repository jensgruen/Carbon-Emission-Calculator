package org.example.DAOImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.example.connection.ConnectionFactory;
import org.example.queries.CrudOperations;
import org.example.DAOInterfaces.EmissionFactorDAO;
import org.example.otherMethods.HelperMethodsClass;
import org.example.modelClasses.EmissionFactor;

/**
 * This is the implementation of the EmissionFactorDAO class.
 * It contains the implementation of the CRUD operations for user objects.
 * There is an automated connection between Java and the SQL database.
 */

public class EmissionFactorDAOImpl implements EmissionFactorDAO {

  @Override
  public List<EmissionFactor> getAllEmissionFactors() {
    String sql = CrudOperations.getAllEmissionFactorsSQL.toString();
    ArrayList<EmissionFactor> emissionFactors = new ArrayList<>();
    System.out.println("{ Factor Id, Activity Id, Factor, Unit }");
    System.out.println();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Long factorId = rs.getLong("factor_id");
        Long activityId = null;
        if (rs.getLong("activity_id") > 0) {
          activityId = rs.getLong("activity_id");
        }
        Double factor = null;
        if (rs.getInt("factor") > -1) {
          factor = rs.getDouble("factor");
        }
        String unit = null;
        if (rs.getString("unit").length() > 0) {
          unit = rs.getString("unit");
        }
        emissionFactors.add(new EmissionFactor(factorId,activityId,factor,unit));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return emissionFactors;
  }

  @Override
  public EmissionFactor getEmissionFactor() {
    Scanner scanner = new Scanner(System.in);
    long factorId = HelperMethodsClass.CheckInputId(scanner, "Enter a factor ID");

      if (factorId <= calculateMaxFactorId()) {
        String sql = CrudOperations.getEmissionFactorSQL.toString() + factorId;
        EmissionFactor emissionFactor = null;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
          ResultSet rs = stmt.executeQuery();
          while (rs.next()) {
            Long factor_id = rs.getLong("factor_id");
            Long activityId = null;
            if (rs.getLong("activity_id") > 0) {
              activityId = rs.getLong("activity_id");
            }
            Double factor = null;
            if (rs.getInt("factor") > -1) {
              factor = rs.getDouble("factor");
            }
            String unit = null;
            if (rs.getString("unit").length() > 0) {
              unit = rs.getString("unit");
            }
            emissionFactor = new EmissionFactor(factor_id, activityId, factor, unit);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return emissionFactor;
      }
      return null;
  }

  @Override
  public void createEmissionFactor() {
    Scanner scanner = new Scanner(System.in);
    long factorId = calculateMaxFactorId()+1;
    long activityId = HelperMethodsClass.CheckInputId(scanner, "Enter a activity_id");
    double factor = HelperMethodsClass.checkInputForDouble(scanner, "Enter a factor");
      System.out.println("Enter a unit");
      String unit = scanner.next();
      String sql = CrudOperations.createEmissionFactorSQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, factorId);
        stmt.setLong(2, activityId);
        stmt.setDouble(3, factor);
        stmt.setString(4, unit);
        stmt.addBatch();
        stmt.executeBatch();
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }

  @Override
  public void updateEmissionFactor() {
    Scanner scanner = new Scanner(System.in);
    long factorId = HelperMethodsClass.CheckInputId(scanner, "Enter a factor_id");
    if (factorId <= calculateMaxFactorId()) {
      long activityId = HelperMethodsClass.CheckInputId(scanner, "Enter a new activity_id");
      double factor = HelperMethodsClass.checkInputForDouble(scanner, "Enter a new factor");
        System.out.println("Enter a new unit");
        String unit = scanner.next();
        String sql = CrudOperations.updateEmissionFactorSQL.toString() + factorId;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
          stmt.setLong(1, activityId);
          stmt.setDouble(2, factor);
          stmt.setString(3, unit);
          stmt.executeUpdate();

        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Wrong input for activity_id or factor, you will go back to the main menu");
        System.out.println();
      }
  }

  @Override
  public void deleteEmissionFactor() {
    Scanner scanner = new Scanner(System.in);
    long factorId = HelperMethodsClass.CheckInputId(scanner, "Enter a factor ID");
    if (factorId <= calculateMaxFactorId()) {
      String sql = CrudOperations.deleteEmissionFactorSQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, factorId);
        stmt.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("This factor does not exist, you will go back to the main menu.");
    }
  }

  private int calculateMaxFactorId () {
    String sql = "select max(factor_id) as max from emission_factors";
    int maximum = 0;
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      while(rs.next()) {
        maximum = rs.getInt("max");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return maximum;
  }


}
