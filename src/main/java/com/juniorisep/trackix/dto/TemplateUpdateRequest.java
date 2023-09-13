package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateUpdateRequest {

    private String name;
    private String subject;
    private String text;
    private String html;


}
