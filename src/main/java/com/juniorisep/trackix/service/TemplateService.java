package com.juniorisep.trackix.service;

import com.juniorisep.trackix.dto.TemplateCreateRequest;
import com.juniorisep.trackix.dto.TemplateUpdateRequest;
import com.juniorisep.trackix.model.Template;
import com.juniorisep.trackix.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Object getAllTemplates() {
        return templateRepository.findAll();
    }


    public Object addTemplate(TemplateCreateRequest templateDto) {

        Template template = Template.builder()
                .name(templateDto.getName())
                .subject(templateDto.getSubject())
                .text(templateDto.getText())
                .html(templateDto.getHtml())
                .modifiedDate(new Date())
                .templateDesign(templateDto.getTemplateDesign())
                .build();

        templateRepository.save(template);

        return template;

    }

    public Object deleteTemplate(int id) {
        templateRepository.deleteById(id);
        return "Template deleted";
    }

    public Object updateTemplate(int id, TemplateUpdateRequest updateDto) {

            Template template = templateRepository.findById(id).orElseThrow(() -> new RuntimeException("Template not found"));

            template.setName(updateDto.getName());
            template.setSubject(updateDto.getSubject());
            template.setText(updateDto.getText());
            template.setHtml(updateDto.getHtml());
            template.setModifiedDate(new Date());
            template.setTemplateDesign(updateDto.getTemplateDesign());

            templateRepository.save(template);

            return template;
    }

    public Object getTemplateById(int id) {
        return templateRepository.findById(id);
    }
}
