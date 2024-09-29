package org.example.DAOImplementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import org.example.connection.ConnectionFactory;
import org.example.queries.CrudOperations;
import org.example.DAOInterfaces.EmissionGoalDAO;
import org.example.otherMethods.HelperMethodsClass;
import org.example.modelClasses.EmissionGoal;

/**
 * This is the implementation of the EmissionGoalDAO class.
 * It contains the implementation of the CRUD operations for user objects.
 * There is an automated connection between Java and the SQL database.
 */

public class EmissionGoalDAOImpl implements EmissionGoalDAO {

  @Override
  public ArrayList<EmissionGoal> getEmissionGoals() {
    String sql = CrudOperations.getEmissionGoalsSQL.toString();
    ArrayList<EmissionGoal> emissionGoals = new ArrayList<>();
    System.out.println("{ Goal Id, User Id, Target emission, Start date, End date, Status }");
    System.out.println();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Long goalId = rs.getLong("goal_id");
        Long userId = null;
        if (rs.getLong("user_id") > 0) {
          userId = rs.getLong("user_id");
        }
        Double targetEmission = null;
        if (rs.getInt("target_emission") > -1) {
          targetEmission = rs.getDouble("target_emission");
        }
        LocalDate startDate =null;
        if (rs.getBoolean("start_date")) {
        startDate = rs.getDate("start_date").toLocalDate();
        }
        LocalDate endDate = null;
        if (rs.getBoolean("end_date")) {
          endDate = rs.getDate("end_date").toLocalDate();
        }
        String status = null;
        if (rs.getString("status").length() > 0) {
          status = rs.getString("status");
        }
        System.out.println("{ " + goalId + ", " + userId +", " + targetEmission+", " +
            startDate+", " + endDate+", "+ status + " }");
        System.out.println();
        emissionGoals.add(new EmissionGoal(goalId,userId,targetEmission,startDate,endDate,status));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return emissionGoals;
  }

  @Override
  public EmissionGoal getEmissionGoal() {
    Scanner scanner = new Scanner(System.in);
    long goalId = HelperMethodsClass.CheckInputId(scanner, "Enter a goal ID");

      if (goalId <= calculateMaxGoalId()) {
        String sql = CrudOperations.getEmissionGoalSQL.toString() + goalId;
        EmissionGoal emissionGoal = null;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
          ResultSet rs = stmt.executeQuery();
          while (rs.next()) {
            Long goal_Id = rs.getLong("goal_id");
            Long userId = null;
            if (rs.getInt("user_id") > 0) {
              userId = rs.getLong("user_id");
            }
            Double targetEmission = null;
            if (rs.getInt("target_emission") > -1) {
              targetEmission = rs.getDouble("target_emission");
            }
            LocalDate startDate = null;
            if (rs.getBoolean("start_date")) {
              startDate = rs.getDate("start_date").toLocalDate();
            }
            LocalDate endDate = null;
            if (rs.getBoolean("end_date")) {
              endDate = rs.getDate("end_date").toLocalDate();
            }
            String status = null;
            if (rs.getString("status").length() > 0) {
              status = rs.getString("status");
            }
            emissionGoal = new EmissionGoal(goal_Id, userId, targetEmission, startDate, endDate,
                status);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return emissionGoal;
      }
      return null;
  }

  @Override
  public void createEmissionGoal() {
    Scanner scanner = new Scanner(System.in);
      long goalId =calculateMaxGoalId()+1;
      long userId = HelperMethodsClass.CheckInputId(scanner, "Enter a user_id");
      double targetEmission = HelperMethodsClass.checkInputForDouble(scanner,"Enter a target Emission" );
      String startDate = HelperMethodsClass.checkDate(scanner, "start");
      String endDate = HelperMethodsClass.checkDate(scanner,"end");
      String status = HelperMethodsClass.checkStatus(scanner);

        String sql = CrudOperations.createEmissionGoalSQL.toString();
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
          stmt.setLong(1, goalId);
          stmt.setLong(2, userId);
          stmt.setDouble(3, targetEmission);
          stmt.setDate(4, Date.valueOf(startDate));
          stmt.setDate(5, Date.valueOf(endDate));
          stmt.setString(6, status);
          stmt.addBatch();
          stmt.executeBatch();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

  @Override
  public void updateEmissionGoal() {
    Scanner scanner = new Scanner(System.in);
    long goalId = HelperMethodsClass.CheckInputId(scanner,"Enter a goal_id" );
    if (goalId <= calculateMaxGoalId()) {
      long userId = HelperMethodsClass.CheckInputId(scanner, "Enter a user_id");
      double targetEmission = HelperMethodsClass.checkInputForDouble(scanner,"Enter a target Emission" );
      String startDate = HelperMethodsClass.checkDate(scanner, "start");
      String endDate = HelperMethodsClass.checkDate(scanner,"end");
      String status = HelperMethodsClass.checkStatus(scanner);

      String sql = CrudOperations.updateEmissionGoalSQL.toString() + goalId;
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
              ResultSet.CONCUR_UPDATABLE)) {
        stmt.setLong(1, userId);
        stmt.setDouble(2, targetEmission);
        stmt.setDate(3, Date.valueOf(startDate));
        stmt.setDate(4, Date.valueOf(endDate));
        stmt.setString(5,status);
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
  public void deleteEmissionGoal() {
    Scanner scanner = new Scanner(System.in);
    long goalId = HelperMethodsClass.CheckInputId(scanner, "Enter a goal ID");
    if (goalId <= calculateMaxGoalId()) {
      String sql = CrudOperations.deleteEmissionGoalSQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, goalId);
        stmt.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("This goal does not exist, you will go back to the main menu.");
    }
  }

  private int calculateMaxGoalId () {
    String sql = "select max(goal_id) as max from emission_goals";
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
