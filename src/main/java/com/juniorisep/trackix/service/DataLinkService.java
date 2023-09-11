package com.juniorisep.trackix.service;

import com.juniorisep.trackix.model.DataLink;
import com.juniorisep.trackix.model.LinkTrack;
import com.juniorisep.trackix.repository.DataLinkRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for the DataLink entity
 * <p>
 *     This class contains all the methods that can be applied to the DataLink entity
 *     This class is used to save the data of the linkTracker
 * </p>
 *
 * @see DataLinkRepository
 * @see DataLink
 * @see com.juniorisep.trackix.controller.TrackerController
 *
 */

@Service
public class DataLinkService {

    private final DataLinkRepository dataLinkRepository;

    public DataLinkService(DataLinkRepository dataLinkRepository) {
        this.dataLinkRepository = dataLinkRepository;
    }


    public void saveDataLink(String userAgent, String clientIpAddress, LinkTrack linkTrack) {

            //create a new data campaign
            DataLink dataLink = DataLink.builder()
                    .linkTrack(linkTrack)
                    .userAgent(userAgent)
                    .ip(clientIpAddress)
                    .Date(new Date())
                    .build();

            //save the data campaign
            dataLinkRepository.save(dataLink);
    }
}
