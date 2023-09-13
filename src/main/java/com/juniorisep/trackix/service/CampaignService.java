package com.juniorisep.trackix.service;

import com.juniorisep.trackix.model.Campaign;
import com.juniorisep.trackix.model.Group;
import com.juniorisep.trackix.model.Smtp;
import com.juniorisep.trackix.model.Template;
import com.juniorisep.trackix.repository.CampaignRepository;
import com.juniorisep.trackix.repository.GroupRepository;
import com.juniorisep.trackix.repository.SmtpRepository;
import com.juniorisep.trackix.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import com.juniorisep.trackix.dto.CampaignCreateRequest;

import java.util.Date;
import java.util.List;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final TemplateRepository templateRepository;
    private final GroupRepository groupRepository;
    private final SmtpRepository smtpRepository;

    public CampaignService(CampaignRepository campaignRepository, TemplateRepository templateRepository, GroupRepository groupRepository, SmtpRepository smtpRepository) {
        this.campaignRepository = campaignRepository;
        this.templateRepository = templateRepository;
        this.groupRepository = groupRepository;
        this.smtpRepository = smtpRepository;
    }

    public Object getAllCampaigns() {

        return campaignRepository.findAll();
    }

    public Object addCampaign(CampaignCreateRequest campaignDto) {

        //get the template from the templateid
        Template template = templateRepository.findById(campaignDto.getTemplateId()).orElseThrow(() -> new RuntimeException("Template not found"));
        // get the groups from the groupids given in the dto, and then create a list containing all the groups
        List<Group> groups = groupRepository.findAllById(campaignDto.getGroupIds());
        System.out.println(groups);

        //get the smtp from the smtpid
        Smtp smtp = smtpRepository.findById(campaignDto.getSmtpId()).orElseThrow(() -> new RuntimeException("Smtp not found"));

        Campaign campaign = Campaign.builder()
                .name(campaignDto.getName())
                .createdDate(new Date())
                .launchDate(campaignDto.getLaunchDate())
                .completedDate(null)
                .template(template)
                .groups(groups)
                .results(null)
                .status(campaignDto.getStatus())
                .smtp(smtp)
                .build();

        campaignRepository.save(campaign);

        return campaign;
    }

    public Object deleteCampaign(int id) {

        campaignRepository.deleteById(id);
    }

    /*public Object updateCampaign(int id, CampaignUpdateRequest updateDto) {
    }*/

    public Object getCampaignById(int id) {

            return campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("Campaign not found"));
    }
}
