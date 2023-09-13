package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.TemplateCreateRequest;
import com.juniorisep.trackix.dto.TemplateUpdateRequest;
import com.juniorisep.trackix.service.TemplateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/template")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("/all")
    public Object getAllTemplates() {
        return templateService.getAllTemplates();
    }

    @PostMapping("/add")
    public Object addTemplate(@RequestBody TemplateCreateRequest templateDto) {
        return templateService.addTemplate(templateDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteTemplate(@PathVariable("id") int id) {
        return templateService.deleteTemplate(id);
    }

    @PutMapping("/update/{id}")
    public Object updateTemplate(@PathVariable("id") int id, @RequestBody TemplateUpdateRequest updateDto) {
        return templateService.updateTemplate(id, updateDto);
    }

}
