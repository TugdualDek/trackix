package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailUpdateRequest {

    private String name;
    private String description;
    private boolean isFinished;

}
