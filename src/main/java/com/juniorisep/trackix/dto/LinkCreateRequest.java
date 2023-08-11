package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkCreateRequest {

    private String name;
    private String description;
    private String linkRedirect;
    
}
