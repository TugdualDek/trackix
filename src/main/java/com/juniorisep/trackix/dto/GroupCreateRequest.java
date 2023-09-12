package com.juniorisep.trackix.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupCreateRequest {

    private String name;
    private String description;
    //array of receivers
    private List<Target> recipients;


}
