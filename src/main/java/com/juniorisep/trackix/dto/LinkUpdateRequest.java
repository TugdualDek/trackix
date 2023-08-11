package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkUpdateRequest {

    private String name;
    private String description;
    private String linkRedirect;
    private boolean isFinished;

}
