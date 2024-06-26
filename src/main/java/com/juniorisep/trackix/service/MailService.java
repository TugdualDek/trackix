package com.juniorisep.trackix.service;

import com.juniorisep.trackix.dto.MailCreateRequest;
import com.juniorisep.trackix.dto.MailUpdateRequest;
import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.repository.MailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for the MailTrack entity
 * <p>
 *     This class contains all the methods that can be applied to the MailTrack entity
 * </p>
 *
 * @see MailRepository
 * @see MailTrack
 * @see com.juniorisep.trackix.controller.MailController
 * @see com.juniorisep.trackix.repository.MailRepository
 */

@Service
public class MailService {

    private final MailRepository mailRepository;
    private final String LINK_URL = "http://137.74.196.178:8000";


    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public Optional<MailTrack> getCampaignById(int id) {
        return mailRepository.findById(id);
    }

    /*public Object getAllActiveCampaigns() {
        //get all campaign and remove the inactive ones
        List<MailTrack> mailTracks = mailRepository.findAll();
        mailTracks.removeIf(MailTrack::isFinished);
        return mailTracks;
    }*/

    public Object addMailTracker(MailCreateRequest campaignDto) {

        //create a new campaign
        MailTrack mailTrack = MailTrack.builder()
                .name(campaignDto.getName())
                .description(campaignDto.getDescription())
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

    public Object deleteMailTrack(int id) {
        //delete the campaign and check if it has been deleted
        if (mailRepository.existsById(id)) {
            mailRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Object updateMailTrack(int id, MailUpdateRequest updateDto) {

        //get the campaign
        Optional<MailTrack> campaignOptional = mailRepository.findById(id);

        //if the campaign exists
        if (campaignOptional.isPresent()) {

            //get the campaign
            MailTrack mailTrack = campaignOptional.get();

            //update the campaign
            mailTrack.setName(updateDto.getName());
            mailTrack.setDescription(updateDto.getDescription());

            //save the campaign
            mailRepository.save(mailTrack);

            return mailTrack;

        } else {
            return Optional.empty();
        }

    }

    public Object getMailTrackDataById(int id) {

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

    public void generateLink(int id) {
        Optional<MailTrack> mailTrack = mailRepository.findById(id);
        mailTrack.ifPresent(mailTrack1 -> {
            if (mailTrack1.getName().equals("jisep")) {
                mailTrack1.setLink(LINK_URL + "/track/jisep/" + mailTrack1.getId() + "/image.png");
            } else {
                mailTrack1.setLink(LINK_URL + "/track/mail/" + mailTrack1.getId() + "/image.png");
            }
            mailRepository.save(mailTrack1);
        });
    }
}
