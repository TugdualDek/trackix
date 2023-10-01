package com.juniorisep.trackix.service;

import com.juniorisep.trackix.model.*;
import com.juniorisep.trackix.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.juniorisep.trackix.dto.CampaignCreateRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final TemplateRepository templateRepository;
    private final GroupRepository groupRepository;
    private final SmtpRepository smtpRepository;

    private final MailRepository mailRepository;
    private final String LINK_URL = "http://137.74.196.178:8000";

    public CampaignService(CampaignRepository campaignRepository, TemplateRepository templateRepository, GroupRepository groupRepository, SmtpRepository smtpRepository, MailRepository mailRepository) {
        this.campaignRepository = campaignRepository;
        this.templateRepository = templateRepository;
        this.groupRepository = groupRepository;
        this.smtpRepository = smtpRepository;
        this.mailRepository = mailRepository;
    }

    public JavaMailSender configureJavaMailSender(Campaign campaign) {
        Smtp smtp = campaign.getSmtp();

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtp.getHost());
        mailSender.setPort(smtp.getPort());
        mailSender.setUsername(smtp.getUsername());
        mailSender.setPassword(smtp.getPassword());
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);


        return mailSender;
    }

    public Object getAllCampaigns() {

        return campaignRepository.findAll();
    }

    public Object addCampaign(CampaignCreateRequest campaignDto) {

        int templateId = campaignDto.getTemplateId();
        System.out.println("Template ID: " + templateId);

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));
        // get the groups from the groupids given in the dto, and then create a list containing all the groups
        List<Group> groups = groupRepository.findAllById(campaignDto.getGroupIds());
        System.out.println(groups);

        //get the smtp from the smtpid
        Smtp smtp = smtpRepository.findById(campaignDto.getSmtpId()).orElseThrow(() -> new RuntimeException("Smtp not found"));

        Campaign campaign = Campaign.builder()
                .name(campaignDto.getName())
                .createdDate(new Date())
                .launchDate(new Date())
                .completedDate(null)
                .template(template)
                .groups(groups)
                .status("En attente")
                .smtp(smtp)
                .build();

        MailTrack mailTrack = MailTrack.builder()
                .name(campaignDto.getName())
                .link(LINK_URL + "/track/mail/" + campaign.getId() + "/image.png")
                .count(0)
                .dataMails(null)
                .campaign(campaign)
                .build();

        campaign.setMailTrack(mailTrack);


        campaignRepository.save(campaign);
        mailRepository.save(mailTrack);

        return campaign;
    }

    public Object deleteCampaign(int id) {

        //check if campaign exists and if it exists delete it otherwise do nothing
        Campaign campaign = campaignRepository.findById(id).orElse(null);
        if (campaign != null) {
            campaignRepository.deleteById(id);
            return "Campaign deleted";
        } else {
            return "Campaign not found";
        }

    }

    /*public Object updateCampaign(int id, CampaignUpdateRequest updateDto) {
    }*/

    public Object getCampaignById(int id) {

            return campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("Campaign not found"));
    }

    public Object sendCampaign(int id) throws UnsupportedEncodingException, MessagingException {

        //get the campaign by its id
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("Campaign not found"));
        //get the smtp from the campaign
        Smtp smtp = campaign.getSmtp();
        //get the template from the campaign
        Template template = campaign.getTemplate();
        //get the groups from the campaign
        List<Group> groups = campaign.getGroups();
        //get the recipients from the groups
        List<Target> recipients = new ArrayList<>();
        for (Group group : groups) {
            recipients.addAll(group.getTargets());
        }

        JavaMailSender mailSender = configureJavaMailSender(campaign);

        //create the base mail config
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(new InternetAddress(smtp.getFromAddress()));
        helper.setSubject(template.getSubject());
        helper.setReplyTo(new InternetAddress(smtp.getFromAddress()));

        //get the mailtracker from the campaign
        MailTrack mailTrack = campaign.getMailTrack();
        String link = mailTrack.getLink();

        //send the campaign
        for (Target recipient : recipients) {

            //make publiposting for each recipient
            String htmlBody = template.getHtml();
            htmlBody = htmlBody.replace("{{user.firstName}}", recipient.getFirstName());
            htmlBody = htmlBody.replace("{{user.lastName}}", recipient.getLastName());
            htmlBody = htmlBody.replace("{{user.email}}", recipient.getEmail());

            // i have also {{mail.tracking}} in my template. But it is in a p tag, and i want to replace it with an image tag. How can i do that?

            htmlBody = htmlBody.replace("{{mail.tracking}}", "<img src=\"" + link + "\"/>");

            helper.setText(htmlBody, true);

            //send email for each recipient
            helper.setTo(recipient.getEmail());
            mailSender.send(message);

            System.out.println("Email sent to " + recipient.getEmail());

        }

        //update the campaign status
        campaign.setStatus("En cours");

        //save the campaign
        campaignRepository.save(campaign);

        return "Campaign sent";

    }
}
