package org.example.modelClasses;

import java.time.LocalDate;

/**
 * This is the UserEmission model class
 */

public class UserEmission {

  private final Long emissionId;
  private final Long userId;
  private final Long activityId;
  private final Double quantity;
  private final Double emission;
  private final LocalDate date;

  public UserEmission(Long emissionId, Long userId, Long activityId, Double quantity,
      Double emission,
      LocalDate date) {
    this.emissionId = emissionId;
    this.userId = userId;
    this.activityId = activityId;
    this.quantity = quantity;
    this.emission = emission;
    this.date = date;
  }

  public Long getEmissionId() {
    return emissionId;
  }

  public Long getUserId() {
    return userId;
  }

  public Long getActivityId() {
    return activityId;
  }

  public Double getQuantity() {
    return quantity;
  }

  public Double getEmission() {
    return emission;
  }

  public LocalDate getDate() {
    return date;
  }
}
