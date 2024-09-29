package org.example.queries;

/**
 * This enum contains the most important sql queries to implement CRUD operations
 * For every table/model class there are different queries.
 * There are also a toString methods included for every query.
 */

public enum CrudOperations {

  getAllUsersSQL {
    public String toString() {
      return "select * from users";
    }
  },

  getUserSQL {
    public String toString() {
      return "select * from users where user_id = ";
    }
  },

  insertUserSQL {
    public String toString() {
      return "insert into users (user_id, username, email) values (?,?,?)";
    }
  },

  updateUserSQL {
    public String toString() {
      return "update users set username = ?, email = ?  where user_id= ";
    }
  },

  deleteUserSQL {
    public String toString() {
      return "delete from users where user_id = ?";
    }
  },

  getAllActivitiesSQL {
    public String toString() {
      return "select * from activities";
    }
  },

  getActivitySQL {
    public String toString() {
      return "select * from activities where user_id = ";
    }
  },

  insertActivitySQL {
    public String toString() {
      return  "insert into activities (activity_id, name, description) values (?,?,?)";
    }
  },

  updateActivitySQL {
    public String toString() {
      return "update activities set name = ?, description = ?  where user_id= ";
    }
  },


  deleteActivitySQL {
    public String toString() {
      return "delete from activities where activity_id = ?";
    }
  },


  getAllEmissionFactorsSQL {
    public String toString() {
      return "select * from emission_factors";
    }
  },

  getEmissionFactorSQL {
    public String toString() {
      return "select * from emission_factors where factor_id = ";
    }
  },

  createEmissionFactorSQL {
    public String toString() {
      return "insert into emission_factors (factor_id, activity_id,factor,unit) values (?,?,?,?,?)";
    }
  },

  updateEmissionFactorSQL {
    public String toString() {
      return "update emission_factors set activity_id = ?,factor =?, unit =?  where factor_id= ";
    }
  },

  deleteEmissionFactorSQL {
    public String toString() {
      return "delete from emission_factors where factor_id = ?";
    }
  },

  getEmissionGoalsSQL {
    public String toString() {
      return  "select * from emission_goals";
    }
  },

  getEmissionGoalSQL {
    public String toString() {
      return  "select * from emission_goals where goal_id = ";
    }
  },

  createEmissionGoalSQL {
    public String toString() {
      return  "insert into emission_goals (goal_Id,user_id,target_emission,start_date,end_date,status) values (?,?,?,?,?,?::emission_goal_status)";
    }
  },

  updateEmissionGoalSQL {
    public String toString() {
      return  "update emission_goals set user_id = ?, target_emission = ?, start_date = ?, end_date = ?, status =?::emission_goal_status  where goal_id= ";
    }
  },

  deleteEmissionGoalSQL {
    public String toString() {
      return  "delete from emission_goals where goal_id = ?";
    }
  },

  getAllUserEmissionsSQL {
    public String toString() {
      return  "select * from user_emissions";
    }
  },

  getUserEmissionSQL {
    public String toString() {
      return  "select * from user_emissions where emission_id = ";
    }
  },

  insertUserEmissionSQL {
    public String toString() {
      return  "insert into user_emissions (emission_id,user_id,activity_id,quantity,emission,date) values (?,?,?,?,?,?)";
    }
  },

  updateUserEmissionSQL {
    public String toString() {
      return  "update user_emissions set user_Id = ?, activity_Id = ?, quantity = ?, emission = ?, date = ?  where emission_id= ";
    }
  },

  deleteUserEmissionSQL {
    public String toString() {
      return  "delete from user_emissions where emission_id = ?";
    }
  }

}
