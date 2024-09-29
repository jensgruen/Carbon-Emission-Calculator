package org.example.otherMethods;

import java.util.Scanner;

/**
 * This class contains some methods to simplify the CRUD operations.
 */

public class HelperMethodsClass {
  public static long CheckInputId (Scanner scanner, String string) {
    long inputId = 0;
    boolean validInputId;
    do {
      validInputId = true;
      System.out.println();
      System.out.println(string);
      try {
        inputId = Integer.parseInt(scanner.next());
      } catch (NumberFormatException e) {
        System.out.println(
            "The entry is not valid.");
        System.out.println();
        validInputId = false;
      }
    } while (!validInputId);
    return inputId;
  }



  public static String checkStringWithoutInteger (Scanner scanner, String string) {
    String inputString = null;
    boolean validString = false;
    //scanner.nextLine();
    while (true) {
      validString = true;
      System.out.println();
      System.out.println(string);
      inputString = scanner.nextLine();
      if (!inputString.matches(".*\\d.*")) {
       break;
      } else {
        System.out.println("Not a valid name.");
        System.out.println();
      }
    }
    return inputString;
  }

  public static double checkInputForDouble (Scanner scanner, String string) {

    double inputId = 0;
    boolean validDouble;
    do {
      validDouble = true;
      System.out.println();
      System.out.println(string);
      try {
        inputId = Double.parseDouble(scanner.next());
      } catch (NumberFormatException e) {
        System.out.println(
            "The entry is not valid.");
        System.out.println();
        validDouble = false;
      }
    } while (!validDouble);
    return inputId;
  }




  public static String checkDate (Scanner scanner, String string) {
   String date ;
    boolean isIsoFormat;
    do {
      isIsoFormat = false;
      System.out.println();
      System.out.println("Enter " + string + " date in format yyyy-MM-dd.");
      date = scanner.next();
//     if (date.equals("skip")) {
//       date ="";
//       break;
//     }
      try {
        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(date);
      } catch (java.time.format.DateTimeParseException e) {
        System.out.println(
            "The format of the date is not valid.");
        System.out.println();
        isIsoFormat = true;
      }
    } while (isIsoFormat);
    return date;
  }

  public static String checkStatus (Scanner scanner) {
    String status;
    scanner.nextLine();
    while (true) {
      System.out.println("Enter a status (achieved, not achieved or unknown):");
      status = scanner.nextLine().toLowerCase();
      if (status.equals("achieved") || status.equals("not achieved") || status.equals("unknown")) {
        break;
      } else {
        System.out.println(
            "This status does not exist, please enter a valid status.");
      }
    }
    return status;
  }



}
