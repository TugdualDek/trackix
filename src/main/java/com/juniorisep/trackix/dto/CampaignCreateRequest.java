package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CampaignCreateRequest {

    private String name;
    private Date launchDate;
    private Date completedDate;
    private int templateId;
    private List<Integer> groupIds;
    private String status;
    private int smtpId;

}
