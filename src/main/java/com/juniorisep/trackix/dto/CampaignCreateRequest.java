package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CampaignCreateRequest {

    private String name;
    private int templateId;
    private List<Integer> groupIds;
    private int smtpId;

}
