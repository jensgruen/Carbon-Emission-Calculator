package org.example.queries;

/**
 * This enum contains the sql queries for the SQL operations.
 * It also includes toString methods for each query.
 */

public enum OperationQueries {
  calculateTotalEmissionsForUserSQL {
    public String toString() {
      return "select u.user_id, sum(ue.emission*ue.quantity) from users u join user_emissions ue on u.user_id = ue.user_id group by u.user_id order by u.user_id";
    }
  },

  compareEmissionsActivitiesSQL {
    public String toString() {
      return  "select a.activity_id, avg(ue.emission) from activities a \n"
          + "\tjoin user_emissions ue on a.activity_id = ue.activity_id group by a. activity_id order by a.activity_id";
    }
  },

  filterByEmissionThresholdSQL {
    public String toString() {
      return "select a.activity_id,ue.user_id, ue.emission from activities a\n"
          + "join user_emissions ue on a.activity_id = ue.activity_id where ue.emission > 10";
    }
  },

  emissionsForEachMonthSQL {
    public String toString() {
      return "select date_part('month',date) as month, sum(emission) as sum_of_emissions \n"
          + "\tfrom user_emissions group by date_part('month',date)";
    }
  },

  userWhoExceedEmissionGoalsSQL {
    public String toString() {
      return """
          select ue.user_id, eg.target_emission, sum(ue.emission*ue.quantity) as emission\s
          \tfrom user_emissions ue join emission_goals eg on ue.user_id = eg.user_id\s
          \tgroup by ue.user_id, eg.target_emission
          \thaving sum(ue.emission*ue.quantity) < eg.target_emission""";
    }
  },

  filterByEnumSQL {
    public String toString() {
      return "select goal_id, user_id, status from emission_goals where status = '";
    }
  },

  topThreeActivitiesSQL {
    public String toString() {
      return """
          select a.name, avg(ue.emission*ue.quantity) as average_total_emission
          from user_emissions ue join\s
          activities a on ue.activity_id = a.activity_id
          group by a.name
          order by avg(ue.emission*ue.quantity)
          limit 3""";
    }
  }


}
