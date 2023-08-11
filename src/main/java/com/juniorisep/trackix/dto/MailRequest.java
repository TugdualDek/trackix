package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {

    private String to;
    private String subject;
    private String body;
    private String from;

}
