package com.juniorisep.trackix.service;

import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.model.DataMail;
import com.juniorisep.trackix.repository.MailRepository;
import com.juniorisep.trackix.repository.DataMailRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for the DataMail entity
 * <p>
 *     This class contains all the methods that can be applied to the DataMail entity
 *     This class is used to save the data of the mailTracker
 * </p>
 *
 * @see DataMailRepository
 * @see DataMail
 * @see com.juniorisep.trackix.controller.TrackerController
 *
 */

@Service
public class DataMailService {

    private final DataMailRepository dataMailRepository;

    public DataMailService(DataMailRepository dataMailRepository) {
        this.dataMailRepository = dataMailRepository;
    }

    public void saveDataCampaign(String userAgent, String clientIpAddress, MailTrack mailTrack) {

        //create a new data campaign
        DataMail dataMail = DataMail.builder()
                .mailTrack(mailTrack)
                .userAgent(userAgent)
                .ip(clientIpAddress)
                .Date(new Date())
                .build();

        //save the data campaign
        dataMailRepository.save(dataMail);


    }
}
