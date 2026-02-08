package com.school.aet.group.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateGroupRequest {
    private String name;
    private Integer ageRangeMin;
    private Integer ageRangeMax;
    private UUID focusDomainId;
    private String description;
    private Boolean active;

    public String getName() { return name; }
    public Integer getAgeRangeMin() { return ageRangeMin; }
    public Integer getAgeRangeMax() { return ageRangeMax; }
    public UUID getFocusDomainId() { return focusDomainId; }
    public String getDescription() { return description; }
    public Boolean getActive() { return active; }
}
