package org.example.modelClasses;

/**
 * This is the activity model class
 */
public class Activity {
  private final Long activityId;
  private final String name;
  private final String description;

  public Activity(Long activityId, String name, String description) {
    this.activityId = activityId;
    this.name = name;
    this.description = description;
  }

  public Long getActivityId() {
    return activityId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
}
