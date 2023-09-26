package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.CampaignCreateRequest;
import com.juniorisep.trackix.service.CampaignService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/all")
    public Object getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @PostMapping("/add")
    public Object addCampaign(@RequestBody CampaignCreateRequest campaignDto) {
        return campaignService.addCampaign(campaignDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteCampaign(@PathVariable("id") int id) {
        return campaignService.deleteCampaign(id);
    }

    /*@PutMapping("/update/{id}")
    public Object updateCampaign(@PathVariable("id") int id, @RequestBody CampaignUpdateRequest updateDto) {
        return campaignService.updateCampaign(id, updateDto);
    }*/

    @GetMapping("/get/{id}")
    public Object getCampaignById(@PathVariable("id") int id) {
        return campaignService.getCampaignById(id);
    }

}
