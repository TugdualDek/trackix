package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendingProfileCreateRequest {

    private String name;
    private String host;
    private String username;
    private String password;
    private int port;
    private String fromAddress;

}
