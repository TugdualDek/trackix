package com.juniorisep.trackix.dto;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailingCreateRequest {

    private String name;
    private String description;
    //array of receivers
    private List<Recipients> recipients;


}
