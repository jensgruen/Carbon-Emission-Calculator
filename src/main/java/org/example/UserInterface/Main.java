package org.example.UserInterface;

import java.sql.Connection;
import java.util.Arrays;
import java.util.Scanner;
import org.example.DAOImplementation.ActivityDAOImpl;
import org.example.DAOImplementation.EmissionFactorDAOImpl;
import org.example.DAOImplementation.EmissionGoalDAOImpl;
import org.example.DAOImplementation.UserDAOImpl;
import org.example.DAOImplementation.UserEmissionDAOImpl;
import org.example.LoginAndRegistration.UserLogin;
import org.example.SQLOperations.Tasks;
import org.example.connection.ConnectionFactory;
import org.example.modelClasses.Activity;
import org.example.modelClasses.EmissionFactor;
import org.example.modelClasses.EmissionGoal;
import org.example.modelClasses.User;
import org.example.modelClasses.UserEmission;


/**
 * This is the main class with the CLI interface.
 * It contains menus to choose SQL operations and performs on a database level.
 * There are CRUD operations and SQL operations.
 * In the menu you can choose one of them.
 */
public class Main {


  public static void main(String[] args) {

    Connection connection = ConnectionFactory.getConnection();
    if (connection != null) {
      System.out.println("Connection established.");
      System.out.println();

      Scanner scanner = new Scanner(System.in);
      UserDAOImpl userDAO = new UserDAOImpl();
      UserEmissionDAOImpl userEmissionDAO = new UserEmissionDAOImpl();
      ActivityDAOImpl activityDAO = new ActivityDAOImpl();
      EmissionFactorDAOImpl emissionFactorDAO = new EmissionFactorDAOImpl();
      EmissionGoalDAOImpl emissionGoalDAO = new EmissionGoalDAOImpl();


      while (true) {
        System.out.println("Please login [1], logout [2] or register [3], press 0 to exit");
        int firstChoice = scanner.nextInt();

        if (firstChoice == 0) {
          break;
        }

        switch (firstChoice) {
          case 1 -> UserLogin.login();
          case 2 -> UserLogin.logout();
          case 3 -> UserLogin.registration();
        }

      }



      while (true) {
        System.out.println("Main Menu:");
        System.out.println("Press 1 for CRUD operations");
        System.out.println("Press 2 for SQL operations");
        System.out.println("Press 3 for help");
        System.out.println("Press 4 to login");
        System.out.println("Press 5 to register a user");
        System.out.println("Press 0 to exit");
        int choice;
        try {
          choice = Integer.parseInt(scanner.next());
        } catch (NumberFormatException e) {
          choice = -1;
        }

        if (choice == 0) {
          break;
        }

        switch (choice) {
          case 1 -> {
            System.out.println("""
                Please select CRUD operation.\s
                \tPress 1 for inserting Data,\s
                \t2 for updating data (You have to update all columns of a record, if they do not change please retype),\s
                \t3 for deleting data,\s
                \t4 for getting a record,\s
                \t5 for getting all records,\s
                \t0 to go back to the main menu.""");
            System.out.println();

            int crudOperation;
            try {
              crudOperation= Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
              crudOperation = -1;
              System.out.println("Wrong input, you will go back to the main menu");
              System.out.println();
            }
            if (crudOperation == 0 || crudOperation == -1) {
              break;
            }
            System.out.println("""
                Select table:\s
                \tPress 1 for users,\s
                \t2 for activities,\s
                \t3 for user emission,\s
                \t4 for emission goal,\s
                \t5 for emission factor, \s
                \t0 to go back to the main menu.""");
            System.out.println();
            int table;
            try {
              table= Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
              table = -1;
              System.out.println("Wrong input, you will go back to the main menu");
              System.out.println();
            }
            if (table == 0 || table == -1) {
              break;
            }
            String switchExpression = crudOperation + String.valueOf(table);

            switch (switchExpression) {
              case "11" -> userDAO.insertUser();
              case "12" -> activityDAO.insertActivity();
              case "13" -> userEmissionDAO.insertUserEmission();
              case "14" -> emissionGoalDAO.createEmissionGoal();
              case "15" -> emissionFactorDAO.createEmissionFactor();
              case "21" -> userDAO.updateUser();
              case "22" -> activityDAO.updateActivity();
              case "23" -> userEmissionDAO.updateUserEmission();
              case "24" -> emissionGoalDAO.updateEmissionGoal();
              case "25" -> emissionFactorDAO.updateEmissionFactor();
              case "31" -> userDAO.deleteUser();
              case "32" -> activityDAO.deleteActivity();
              case "33" -> userEmissionDAO.deleteUserEmission();
              case "34" -> emissionGoalDAO.deleteEmissionGoal();
              case "35" -> emissionFactorDAO.deleteEmissionFactor();
              case "41" -> {
                User user = userDAO.getUser();
                if (user != null) {
                  System.out.println("User ID: " + user.getUserId());
                  System.out.println("Username: " + user.getUsername());
                  System.out.println("Email: " + user.getEmail());
                  System.out.println();
                } else {
                  System.out.println("This user does not exist, you will go back to the main menu.");
                  System.out.println();
                }
              }
              case "42" -> {
                Activity activity = activityDAO.getActivity();
                if (activity != null) {
                  System.out.println("Activity Id: " + activity.getActivityId());
                  System.out.println("Name: " + activity.getName());
                  System.out.println("Description: " + activity.getDescription());
                  System.out.println();
                } else {
                  System.out.println("This activity does not exist, you will go back to the main menu.");
                  System.out.println();
                }
              }
              case "43" -> {
                UserEmission userEmission = userEmissionDAO.getUserEmission();
                if (userEmission != null) {
                  System.out.println("Emission Id: " + userEmission.getEmissionId());
                  System.out.println("User Id: " + userEmission.getUserId());
                  System.out.println("Activity Id: " + userEmission.getActivityId());
                  System.out.println("Quantity: " + userEmission.getQuantity());
                  System.out.println("Emission: " + userEmission.getEmission());
                  System.out.println("Date: " + userEmission.getDate());
                  System.out.println();
                } else {
                  System.out.println("This emission does not exist, you will go back to the main menu.");
                  System.out.println();
                }
              }
              case "44" -> {
                EmissionGoal emissionGoal = emissionGoalDAO.getEmissionGoal();
                if (emissionGoal != null) {
                  System.out.println("Goal Id: " + emissionGoal.getGoalId());
                  System.out.println("User Id: " + emissionGoal.getUserId());
                  System.out.println("Target emission: " + emissionGoal.getTargetEmission());
                  System.out.println("Start date: " + emissionGoal.getStartDate());
                  System.out.println("End date: " + emissionGoal.getEndDate());
                  System.out.println("Status: " + emissionGoal.getStatus());
                  System.out.println();
                } else {
                  System.out.println("This goal does not exist, you will go back to the main menu.");
                  System.out.println();
                }

              }

              case "45" -> {
                EmissionFactor emissionFactor = emissionFactorDAO.getEmissionFactor();
                if (emissionFactor != null) {
                  System.out.println("Factor Id: " + emissionFactor.getFactorId());
                  System.out.println("Activity Id: " + emissionFactor.getActivityId());
                  System.out.println("Factor: " + emissionFactor.getFactor());
                  System.out.println("Unit: " + emissionFactor.getUnit());
                  System.out.println();
                } else {
                  System.out.println("This factor does not exist, you will go back to the main menu.");
                  System.out.println();
                }

              }

              case "51" -> userDAO.getAllUsers();
              case "52" -> activityDAO.getAllActivities();
              case "53" -> userEmissionDAO.getAllUserEmissions();
              case "54" -> emissionGoalDAO.getEmissionGoals();
              case "55" -> emissionFactorDAO.getAllEmissionFactors();
              default -> System.out.println("Something went wrong, please try again");
            }
          }
          case 2 -> {
            System.out.println("""
                select SQL operation :\s
                \tPress 1 for calculating the total emissions for a user,\s
                \t2 for comparing the average emissions for each activity,\s
                \t3 for listing activities with emissions above a certain threshold,\s
                \t4 for calculating the monthly emissions for a user,\s
                \t5 for finding users who have exceeded their emission goals,\s
                \t6 for filtering by custom enum types in queries,\s
                \t7 for identifying top 3 activities with the highest average emissions.""");
            System.out.println();
            int sqlOperation;
            try {
              sqlOperation = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
              sqlOperation = -1;
            }
            switch (sqlOperation) {
              case 1 -> {
                for (int i = 0; i < Tasks.calculateTotalEmissionsForUser().size(); i++) {
                  System.out.println(
                      Arrays.toString(Tasks.calculateTotalEmissionsForUser().get(i).get(i)));
                }
              }
              case 2 -> {
                for (int i = 0; i < Tasks.compareEmissionsActivities().size(); i++) {
                  System.out.println(
                      Arrays.toString(Tasks.compareEmissionsActivities().get(i).get(i)));
                }
              }
              case 3 -> {
                for (int i = 0; i < Tasks.filterByEmissionThreshold().size(); i++) {
                  System.out.println(
                      Arrays.toString(Tasks.filterByEmissionThreshold().get(i).get(i)));
                }
              }
              case 4 -> {
                for (int i = 0; i < Tasks.emissionsForEachMonth().size(); i++) {
                  System.out.println(Arrays.toString(Tasks.emissionsForEachMonth().get(i).get(i)));
                }
              }
              case 5 -> {
                for (int i = 0; i < Tasks.userWhoExceedEmissionGoals().size(); i++) {
                  System.out.println(
                      Arrays.toString(Tasks.userWhoExceedEmissionGoals().get(i).get(i)));
                }
              }
              case 6 -> {
                System.out.println("""
                    Enter a custom enum you want to filter by.\s
                    \tPress 1 for achieved,\s
                    \t 2 for not achieved,\s
                    \t 3 for unknown.""");
                int enumNumber = scanner.nextInt();
                String customEnum = null;
                switch (enumNumber) {
                  case 1 -> customEnum = "achieved";
                  case 2 -> customEnum = "not achieved";
                  case 3 -> customEnum = "unknown";
                  default -> System.out.println("Something went wrong please try again");
                }
                for (int i = 0; i < Tasks.filterByEnum(customEnum).size(); i++) {
                  System.out.println(Arrays.toString(Tasks.filterByEnum(customEnum).get(i).get(i)));
                }
              }
              case 7 -> {
                for (int i = 0; i < Tasks.topThreeActivities().size(); i++) {
                  System.out.println(Arrays.toString(Tasks.topThreeActivities().get(i).get(i)));
                }
              }
              case -1 -> {
                System.out.println("Wrong input, you will go back to main menu.");
                System.out.println();
              }
              default -> System.out.println("Something went wrong, please try again");
            }
          }
          case 3 -> {
            System.out.println("""
                You should choose a database operation.
                The CRUD operation are : insert, update, delete, get an entry and get all entries.
                The SQL operations perform some operations on a database level, please press 2 to select an operation.
                """);
          }
          case -1 -> {
            System.out.println("Wrong input");
            System.out.println();
          }
          default -> {
            System.out.println("You entered a wrong number, please try again.");
            System.out.println();
          }
        }
      }

      } else{
        System.out.println("Connection not established");
      }
    }

  }