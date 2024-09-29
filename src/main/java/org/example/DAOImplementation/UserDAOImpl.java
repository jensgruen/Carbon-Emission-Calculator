package org.example.DAOImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import org.example.connection.ConnectionFactory;
import org.example.queries.CrudOperations;
import org.example.DAOInterfaces.UserDAO;
import org.example.otherMethods.HelperMethodsClass;
import org.example.modelClasses.User;

/**
 * This is the implementation of the UserDAO class.
 * It contains the implementation of the CRUD operations for user objects.
 * There is an automated connection between Java and the SQL database.
 */



public class UserDAOImpl implements UserDAO {

  @Override
  public ArrayList<User> getAllUsers() {
    String sql = CrudOperations.getAllUsersSQL.toString();
    ArrayList<User> users = new ArrayList<>();
    System.out.println("{ User Id, Username, Email}");
    System.out.println();
    try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          Long userId = rs.getLong("user_id");
          String username = null;
          if (rs.getString("username").length() > 0) {
            username = rs.getString("username");
          }
          String email = null;
          if (rs.getString("email").length() > 0) {
            email = rs.getString("email");
          }
          users.add(new User(userId,username,email));
        }
        rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  @Override
  public User getUser() {
    Scanner scanner = new Scanner(System.in);
    long user_id = HelperMethodsClass.CheckInputId(scanner, "Enter a user Id");

      if (user_id <= calculateMaxUserId()) {
        String sql = CrudOperations.getUserSQL.toString() + user_id;
        User user = null;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
          ResultSet rs = stmt.executeQuery();
          while (rs.next()) {
            Long userId = rs.getLong("user_id");
            String username = null;
            if (rs.getString("username").length() > 0) {
              username = rs.getString("username");
            }
            String email = null;
            if (rs.getString("email").length() > 0) {
              email = rs.getString("email");
            }
            user = new User(userId, username, email);
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        return user;
      }
      return null;
  }

  @Override
  public void insertUser () {
    Scanner scanner = new Scanner(System.in);
    long user_id = calculateMaxUserId()+1;
    System.out.println("Enter a username");
    String username = scanner.next();
    System.out.println("Enter an email");
    String email = scanner.next();
    String sql = CrudOperations.insertUserSQL.toString();
    try (Connection conn = ConnectionFactory.getConnection();
    PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1,user_id);
      stmt.setString(2,username);
      stmt.setString(3,email);
      stmt.addBatch();
      stmt.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void updateUser() {
    Scanner scanner = new Scanner(System.in);
    long user_id = HelperMethodsClass.CheckInputId(scanner, "Enter a user Id");

      if (user_id <= calculateMaxUserId()) {
        System.out.println("Enter a new username");
        String username = scanner.next();
        System.out.println("Enter a new email");
        String email = scanner.next();
        String sql = CrudOperations.updateUserSQL.toString() + user_id;
        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
          stmt.setString(1, username);
          stmt.setString(2, email);
          stmt.executeUpdate();

        } catch (SQLException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("The user Id does not exist, you will go back to the main menu.");
        System.out.println();
      }
    }

  @Override
  public void deleteUser() {
    Scanner scanner = new Scanner(System.in);
    long user_id = HelperMethodsClass.CheckInputId(scanner, "Enter a user ID");
    if (user_id <= calculateMaxUserId()) {
      String sql = CrudOperations.deleteUserSQL.toString();
      try (Connection conn = ConnectionFactory.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, user_id);
        stmt.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("This user does not exist, you will go back to the main menu.");
    }
  }

  private int calculateMaxUserId () {
    String sql = "select max(user_id) as max from users";
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
