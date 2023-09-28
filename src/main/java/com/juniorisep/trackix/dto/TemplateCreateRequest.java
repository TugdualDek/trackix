package com.juniorisep.trackix.dto;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Getter
@Setter
public class TemplateCreateRequest {

    private String name;
    private String subject;
    private String text;
    private Text html;
    private Text templateDesign;

}
