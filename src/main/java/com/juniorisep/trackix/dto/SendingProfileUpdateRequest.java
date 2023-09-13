package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendingProfileUpdateRequest {

        private String name;
        private String host;
        private String username;
        private String password;
        private int port;
        private String fromAddress;

}
