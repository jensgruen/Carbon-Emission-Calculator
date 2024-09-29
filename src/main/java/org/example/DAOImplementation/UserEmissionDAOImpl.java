package org.example.DAOImplementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.example.connection.ConnectionFactory;
import org.example.queries.CrudOperations;
import org.example.DAOInterfaces.UserEmissionDAO;
import org.example.otherMethods.HelperMethodsClass;
import org.example.modelClasses.UserEmission;

public class UserEmissionDAOImpl implements UserEmissionDAO {

  @Override
  public List<UserEmission> getAllUserEmissions() {
    String sql = CrudOperations.getAllUserEmissionsSQL.toString();
    ArrayList<UserEmission> userEmissions = new ArrayList<>();
    System.out.println("{ Emission Id, User Id, Activity Id, Quantity, Emission, Date }");
    System.out.println();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Long emissionId = rs.getLong("emission_Id");
        Long userId = null;
        if (rs.getLong("user_id") > 0) {
          userId = rs.getLong("user_id");
        }
        Long activityId = null;
        if (rs.getLong("activity_id") > 0) {
          activityId = rs.getLong("activity_id");
        }
        Double quantity = null;
        if (rs.getInt("quantity") > -1) {
          quantity= rs.getDouble("quantity");
        }
        Double emission = null;
        if (rs.getInt("emission") > -1) {
          emission = rs.getDouble("emission");
        }
        LocalDate date =null;
        if (rs.getBoolean("date")) {
          date = rs.getDate("date").toLocalDate();
        }
        userEmissions.add(new UserEmission(emissionId,userId,activityId,quantity,emission,date));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return userEmissions;
  }

  @Override
  public UserEmission getUserEmission() {
    Scanner scanner = new Scanner(System.in);
    long emissionId = HelperMethodsClass.CheckInputId(scanner, "Enter a emission ID");

      if (emissionId <= calculateMaxEmissionId()) {
        String sql = CrudOperations.getUserEmissionSQL.toString() + emissionId;
        UserEmission userEmission = null;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
          ResultSet rs = stmt.executeQuery();
          while (rs.next()) {
            Long emission_id = rs.getLong("emission_Id");
            Long userId = null;
            if (rs.getLong("user_id") > 0) {
              userId = rs.getLong("user_id");
            }
            Long activityId = null;
            if (rs.getLong("activity_id") > 0) {
              activityId = rs.getLong("activity_id");
            }
            Double quantity = null;
            if (rs.getInt("quantity") > -1) {
              quantity = rs.getDouble("quantity");
            }
            Double emission = null;
            if (rs.getInt("emission") > -1) {
              emission = rs.getDouble("emission");
            }
            LocalDate date = null;
            if (rs.getBoolean("date")) {
              date = rs.getDate("date").toLocalDate();
            }
            userEmission = new UserEmission(emission_id, userId, activityId, quantity, emission,
                date);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return userEmission;
      }
      return null;
  }

  @Override
  public void insertUserEmission() {
    Scanner scanner = new Scanner(System.in);
    long emissionId = calculateMaxEmissionId()+1;
    long userId = HelperMethodsClass.CheckInputId(scanner, "Enter a user_id");
    long activityId = HelperMethodsClass.CheckInputId(scanner, "Enter an activity_id");
    double quantity = HelperMethodsClass.checkInputForDouble(scanner,"Enter a quantity");
    double emission = HelperMethodsClass.checkInputForDouble(scanner, "Enter an emission");
    String date = HelperMethodsClass.checkDate(scanner, "");

      String sql = CrudOperations.insertUserEmissionSQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, emissionId);
        stmt.setLong(2, userId);
        stmt.setLong(3, activityId);
        stmt.setDouble(4, quantity);
        stmt.setDouble(5, emission);
        stmt.setDate(6, Date.valueOf(date));
        stmt.addBatch();
        stmt.executeBatch();
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }

  @Override
  public void updateUserEmission() {
    Scanner scanner = new Scanner(System.in);
    long emissionId = HelperMethodsClass.CheckInputId(scanner, "Enter a emission ID");
    if (emissionId <= calculateMaxEmissionId()) {

      long userId = HelperMethodsClass.CheckInputId(scanner, "Enter a user_id");
      long activityId = HelperMethodsClass.CheckInputId(scanner, "Enter an activity_id");
      double quantity = HelperMethodsClass.checkInputForDouble(scanner, "Enter a quantity");
      double emission = HelperMethodsClass.checkInputForDouble(scanner, "Enter an emission");
      String date = HelperMethodsClass.checkDate(scanner, "");

        String sql = CrudOperations.updateUserEmissionSQL.toString() + emissionId;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
          stmt.setLong(1, userId);
          stmt.setLong(2, activityId);
          stmt.setDouble(3, quantity);
          stmt.setDouble(4, emission);
          stmt.setDate(5, Date.valueOf(date));
          stmt.executeUpdate();

        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("wrong input, you will go back to main menu");
        System.out.println();
      }
  }

  @Override
  public void deleteUserEmission() {
    Scanner scanner = new Scanner(System.in);
    long emissionId = HelperMethodsClass.CheckInputId(scanner, "Enter a emission ID");
    if (emissionId <= calculateMaxEmissionId() && emissionId !=-1) {
      String sql = CrudOperations.deleteUserEmissionSQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, emissionId);
        stmt.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("The emission Id does not exist, you will go back to the main menu.");
    }
  }

  private int calculateMaxEmissionId () {
    String sql = "select max(emission_id) as max from user_emissions";
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
