package org.example.SQLOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.example.connection.ConnectionFactory;
import org.example.queries.OperationQueries;

/**
 * This class contains the implementation of the SQL operations.
 * Each method contains a connection to the database (using the Connection Factory),
 * a prepared statement and a resultSet and operations on this resultSet.
 * The entries are being saved into an array of String arrays.
 */


public class Tasks {

  public static ArrayList<ArrayList<String[]>> calculateTotalEmissionsForUser () {
    String sql = OperationQueries.calculateTotalEmissionsForUserSQL.toString();
    ArrayList<ArrayList<String[]>> totalEmissions = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        ArrayList<String[]> arrayList = new ArrayList<>();
        while(rs.next()) {
          long user_id = rs.getLong(1);
          double sum = rs.getDouble(2);
          arrayList.add(new String[]{Long.toString(user_id), Double.toString(sum)});
          totalEmissions.add(arrayList);
        }

    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return totalEmissions;
  }

  public static ArrayList<ArrayList<String[]>> compareEmissionsActivities (){
    String sql = OperationQueries.compareEmissionsActivitiesSQL.toString();
    ArrayList<ArrayList<String[]>> avgEmissionsActivity = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      ArrayList<String[]> arrayList = new ArrayList<>();
      while(rs.next()) {
        long activity_id = rs.getLong(1);
        double avg = rs.getDouble(2);
        arrayList.add(new String[]{Long.toString(activity_id), Double.toString(avg)});
        avgEmissionsActivity.add(arrayList);
      }

    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return avgEmissionsActivity;
  }

  public static ArrayList<ArrayList<String[]>> filterByEmissionThreshold () {
    String sql = OperationQueries.filterByEmissionThresholdSQL.toString();
    ArrayList<ArrayList<String[]>> emissionThreshold = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      ArrayList<String[]> arrayList = new ArrayList<>();
      while (rs.next()) {
        long activity_id = rs.getLong(1);
        long user_id = rs.getLong(2);
        double threshold = rs.getDouble(3);
        arrayList.add(
            new String[]{Long.toString(activity_id), Long.toString(user_id),
                Double.toString(threshold)});
        emissionThreshold.add(arrayList);
      }
    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return emissionThreshold;
  }

  public static ArrayList<ArrayList<String[]>> emissionsForEachMonth () {
    String sql =OperationQueries.emissionsForEachMonthSQL.toString();
    ArrayList<ArrayList<String[]>> monthlyEmissions = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      ArrayList<String[]> arrayList = new ArrayList<>();
      while (rs.next()) {
        int date = rs.getInt(1);
        double emissions = rs.getDouble(2);
        arrayList.add(
            new String[]{Integer.toString(date), Double.toString(emissions)});
        monthlyEmissions.add(arrayList);
      }
    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return monthlyEmissions;
  }

  public static ArrayList<ArrayList<String[]>> userWhoExceedEmissionGoals () {
    String sql =OperationQueries.userWhoExceedEmissionGoalsSQL.toString();
    ArrayList<ArrayList<String[]>> exceededEmissions = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      ArrayList<String[]> arrayList = new ArrayList<>();
      while (rs.next()) {
        long user_id = rs.getLong(1);
        double target_emissions = rs.getDouble(2);
        double emission = rs.getDouble(3);
        arrayList.add(
            new String[]{Long.toString(user_id), Double.toString(target_emissions),
                Double.toString(emission)});
        exceededEmissions.add(arrayList);
      }
    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return exceededEmissions;
  }


  public static ArrayList<ArrayList<String[]>> filterByEnum (String customEnum) {
    String sql = OperationQueries.filterByEnumSQL.toString() + customEnum + "'";
    ArrayList<ArrayList<String[]>> filterEnum = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      ArrayList<String[]> arrayList = new ArrayList<>();
      while (rs.next()) {
        long goal_id = rs.getLong(1);
        long user_id = rs.getLong(2);
        String status = rs.getString(3);
        arrayList.add(
            new String[]{Long.toString(goal_id), Long.toString(user_id), status});
        filterEnum.add(arrayList);
      }
    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return filterEnum;
  }


  public static ArrayList<ArrayList<String[]>> topThreeActivities () {
    String sql =OperationQueries.topThreeActivitiesSQL.toString();
    ArrayList<ArrayList<String[]>> topThreeAverage = new ArrayList<>();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      ResultSet rs = stmt.executeQuery();
      ArrayList<String[]> arrayList = new ArrayList<>();
      while (rs.next()) {
        String name = rs.getString(1);
        double avg = rs.getDouble(2);
        arrayList.add(
            new String[]{name, Double.toString(avg)});
        topThreeAverage.add(arrayList);
      }
    } catch (SQLException e) {
      System.out.println("Something went wrong. "+ e.getMessage());
    }
    return topThreeAverage;
  }


}
