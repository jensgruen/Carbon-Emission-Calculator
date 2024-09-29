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
import org.example.DAOInterfaces.ActivityDAO;
import org.example.otherMethods.HelperMethodsClass;
import org.example.modelClasses.Activity;

/**
 * This is the implementation of the ActivityDAO class.
 * It contains the implementation of the CRUD operations for user objects.
 * There is an automated connection between Java and the SQL database.
 */

public class ActivityDAOImpl implements ActivityDAO {

  @Override
  public List<Activity> getAllActivities() {
    String sql = CrudOperations.getAllActivitiesSQL.toString();
    ArrayList<Activity> activities = new ArrayList<>();
    System.out.println("{ Activity Id, Name, Description}");
    System.out.println();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Long activityId = rs.getLong("activity_id");
        String name = null;
        if (rs.getString("name").length() > 0) {
          name = rs.getString("name");
        }
        String description = null;
        if (rs.getString("description").length() > 0) {
          description = rs.getString("description");
        }
        activities.add(new Activity(activityId, name, description));
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return activities;
  }



  @Override
  public Activity getActivity() {
    Scanner scanner = new Scanner(System.in);
    long activity_id= HelperMethodsClass.CheckInputId(scanner, "Enter an activity Id");
      if (activity_id <= calculateMaxActivityId()) {
        String sql = CrudOperations.getActivitySQL.toString() + activity_id;
        Activity activity = null;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
          ResultSet rs = stmt.executeQuery();
          while (rs.next()) {
            Long activityId = rs.getLong("activity_id");
            String name = null;
            if (rs.getString("name").length() > 0) {
              name = rs.getString("name");
            }
            String description = null;
            if (rs.getString("description").length() > 0) {
              description = rs.getString("description");
            }

            activity = new Activity(activityId, name, description);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return activity;
      }
      return null;
  }

  @Override
  public void insertActivity() {
    Scanner scanner = new Scanner(System.in);
    long activity_id = calculateMaxActivityId() + 1;
    String name = HelperMethodsClass.checkStringWithoutInteger(scanner, "Enter a name");
    System.out.println("Enter an description");
    String description = scanner.next();

      String sql = CrudOperations.insertActivitySQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, activity_id);
        stmt.setString(2, name);
        stmt.setString(3, description);
        stmt.addBatch();
        stmt.executeBatch();
      } catch (SQLException e) {
        e.printStackTrace();
      }
  }

  @Override
  public void updateActivity() {
    Scanner scanner = new Scanner(System.in);
    long activity_id=HelperMethodsClass.CheckInputId(scanner, "Enter an activity Id");

    if (activity_id <= calculateMaxActivityId()) {
      String name = HelperMethodsClass.checkStringWithoutInteger(scanner, "Enter a new name");
        System.out.println("Enter a new description");
        String description = scanner.next();

        String sql = CrudOperations.updateActivitySQL.toString() + activity_id;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
          stmt.setString(1, name);
          stmt.setString(2, description);
          stmt.executeUpdate();

        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Wrong input name, you will go back to the main menu");
        System.out.println();
      }
    }


  @Override
  public void deleteActivity() {
    Scanner scanner = new Scanner(System.in);
    long activity_id=HelperMethodsClass.CheckInputId(scanner, "Enter an activity ID");
    if (activity_id <= calculateMaxActivityId()) {
      String sql = CrudOperations.deleteActivitySQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, activity_id);
        stmt.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("This activity does not exist, you will go back to the main menu.");
    }
  }

  private int calculateMaxActivityId () {
    String sql = "select max(activity_id) as max from activities";
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
