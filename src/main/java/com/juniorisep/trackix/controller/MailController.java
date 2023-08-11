package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.MailCreateRequest;
import com.juniorisep.trackix.dto.MailUpdateRequest;
import com.juniorisep.trackix.service.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/all")
    public Object getAllCampaigns() {
        return mailService.getAllCampaigns();
    }

    @GetMapping("/all/active")
    public Object getAllActiveCampaigns() {
        return mailService.getAllActiveCampaigns();
    }

    @PostMapping("/add")
    public Object addCampaign(@RequestBody MailCreateRequest campaignDto) {
        return mailService.addCampaign(campaignDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteCampaign(@PathVariable("id") int id) {
        return mailService.deleteCampaign(id);
    }

    @PutMapping("/update/{id}")
    public Object updateCampaign(@PathVariable("id") int id, @RequestBody MailUpdateRequest updateDto) {
        return mailService.updateCampaign(id, updateDto);
    }

    @GetMapping("/get/{id}")
    public Object getCampaignById(@PathVariable("id") int id) {
        return mailService.getCampaignById(id);
    }

    @GetMapping("/get/{id}/data")
    public Object getCampaignDataById(@PathVariable("id") int id) {
        return mailService.getCampaignDataById(id);
    }

    @GetMapping("/{id}/generate")
    public Object generateLink(@PathVariable("id") int id) {
        return mailService.generateLink(id);
    }

}
