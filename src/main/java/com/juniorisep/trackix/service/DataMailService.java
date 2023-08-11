package com.juniorisep.trackix.service;

import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.model.DataMail;
import com.juniorisep.trackix.repository.MailRepository;
import com.juniorisep.trackix.repository.DataMailRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

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
