package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateCreateRequest {

    private String name;
    private String subject;
    private String text;
    private String html;
    private String templateDesign;

}
