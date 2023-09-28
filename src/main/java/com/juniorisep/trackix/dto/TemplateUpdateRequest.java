package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Getter
@Setter
public class TemplateUpdateRequest {

    private String name;
    private String subject;
    private String text;
    private String html;
    private String templateDesign;


}
