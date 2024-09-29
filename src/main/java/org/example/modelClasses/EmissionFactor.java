package org.example.modelClasses;


/**
 * This is the emission_factor model class
 */
public class EmissionFactor {

  private final Long factorId;
  private final Long activityId;
  private final Double factor;
  private final String unit;

  public EmissionFactor(Long factorId, Long activityId, Double factor, String unit) {
    this.factorId = factorId;
    this.activityId = activityId;
    this.factor = factor;
    this.unit = unit;
  }

  public Long getFactorId() {
    return factorId;
  }

  public Long getActivityId() {
    return activityId;
  }

  public Double getFactor() {
    return factor;
  }

  public String getUnit() {
    return unit;
  }
}
