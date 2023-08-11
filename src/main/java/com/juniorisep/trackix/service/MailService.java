package com.juniorisep.trackix.service;

import com.juniorisep.trackix.dto.MailCreateRequest;
import com.juniorisep.trackix.dto.MailUpdateRequest;
import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.repository.MailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MailService {

    private final MailRepository mailRepository;


    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public Optional<MailTrack> getCampaignById(int id) {
        return mailRepository.findById(id);
    }

    public Object getAllCampaigns() {
        return mailRepository.findAll();
    }

    public Object getAllActiveCampaigns() {
        //get all campaign and remove the inactive ones
        List<MailTrack> mailTracks = mailRepository.findAll();
        mailTracks.removeIf(MailTrack::isFinished);
        return mailTracks;
    }

    public Object addCampaign(MailCreateRequest campaignDto) {

        //create a new campaign
        MailTrack mailTrack = MailTrack.builder()
                .name(campaignDto.getName())
                .description(campaignDto.getDescription())
                .startDate(new java.util.Date())
                .isFinished(false)
                .link("")
                .count(0)
                .dataMails(null)
                .build();

        //save the campaign
        mailRepository.save(mailTrack);
        //generate the link
        generateLink(mailTrack.getId());

        return mailTrack;

    }

    public Object deleteCampaign(int id) {
        //delete the campaign and check if it has been deleted
        if (mailRepository.existsById(id)) {
            mailRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Object updateCampaign(int id, MailUpdateRequest updateDto) {

        //get the campaign
        Optional<MailTrack> campaignOptional = mailRepository.findById(id);

        //if the campaign exists
        if (campaignOptional.isPresent()) {

            //get the campaign
            MailTrack mailTrack = campaignOptional.get();

            //update the campaign
            mailTrack.setName(updateDto.getName());
            mailTrack.setDescription(updateDto.getDescription());
            mailTrack.setFinished(updateDto.isFinished());

            //save the campaign
            mailRepository.save(mailTrack);

            return mailTrack;

        } else {
            return Optional.empty();
        }

    }

    public Object getCampaignDataById(int id) {

        Optional<MailTrack> campaign = mailRepository.findById(id);

        if (campaign.isPresent()) {
            return campaign.get().getDataMails();
        } else {
            return Optional.empty();
        }

    }

    public void increaseCount(int id) {
        Optional<MailTrack> mailTrack = mailRepository.findById(id);
        mailTrack.ifPresent(mailTrack1 -> {
            mailTrack1.setCount(mailTrack1.getCount() + 1);
            mailRepository.save(mailTrack1);
        });
    }

    public Object generateLink(int id) {
        Optional<MailTrack> mailTrack = mailRepository.findById(id);
        mailTrack.ifPresent(mailTrack1 -> {
            mailTrack1.setLink("http://localhost:8080/track/mail/" + mailTrack1.getId());
            mailRepository.save(mailTrack1);
        });
        return mailTrack;
    }
}
