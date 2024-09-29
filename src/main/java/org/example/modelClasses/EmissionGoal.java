package org.example.modelClasses;

import java.time.LocalDate;

/**
 * This is the EmissionGoal model class
 */

public class EmissionGoal {

  private final Long goalId;
  private final Long userId;
  private final Double targetEmission;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final String status;

  public EmissionGoal(Long goalId, Long userId, Double targetEmission, LocalDate startDate,
      LocalDate endDate, String status) {
    this.goalId = goalId;
    this.userId = userId;
    this.targetEmission = targetEmission;
    this.startDate = startDate;
    this.endDate = endDate;
    this.status = status;
  }

  public Long getGoalId() {
    return goalId;
  }

  public Long getUserId() {
    return userId;
  }

  public Double getTargetEmission() {
    return targetEmission;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public String getStatus() {
    return status;
  }
}
