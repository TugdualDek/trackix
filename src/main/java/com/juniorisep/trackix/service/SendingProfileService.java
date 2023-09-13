package com.juniorisep.trackix.service;

import com.juniorisep.trackix.dto.SendingProfileCreateRequest;
import com.juniorisep.trackix.dto.SendingProfileUpdateRequest;
import com.juniorisep.trackix.model.Smtp;
import com.juniorisep.trackix.repository.SendingProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SendingProfileService {

    private final SendingProfileRepository sendingProfileRepository;

    public SendingProfileService(SendingProfileRepository sendingProfileRepository) {
        this.sendingProfileRepository = sendingProfileRepository;
    }


    public Object getAllSendingProfiles() {
        return sendingProfileRepository.findAll();
    }

    public Object addSendingProfile(SendingProfileCreateRequest sendingProfileDto) {

        Smtp smtp = Smtp.builder()
                .name(sendingProfileDto.getName())
                .host(sendingProfileDto.getHost())
                .username(sendingProfileDto.getUsername())
                .password(sendingProfileDto.getPassword())
                .port(sendingProfileDto.getPort())
                .fromAddress(sendingProfileDto.getFromAddress())
                .modifiedDate(new Date())
                .build();

        sendingProfileRepository.save(smtp);

        return smtp;

    }

    public Object deleteSendingProfile(int id) {
        //check if the sending profile exists
        sendingProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Sending profile not found"));
        //if it exists then delete it
        sendingProfileRepository.deleteById(id);
    }

    public Object updateSendingProfile(int id, SendingProfileUpdateRequest updateDto) {

        Smtp smtp = sendingProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Sending profile not found"));

        smtp.setName(updateDto.getName());
        smtp.setHost(updateDto.getHost());
        smtp.setUsername(updateDto.getUsername());
        smtp.setPassword(updateDto.getPassword());
        smtp.setPort(updateDto.getPort());
        smtp.setFromAddress(updateDto.getFromAddress());
        smtp.setModifiedDate(new Date());

        sendingProfileRepository.save(smtp);

        return smtp;
    }
}
