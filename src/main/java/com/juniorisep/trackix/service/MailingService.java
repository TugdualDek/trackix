package com.juniorisep.trackix.service;

import com.juniorisep.trackix.dto.MailingCreateRequest;
import com.juniorisep.trackix.dto.Recipients;
import com.juniorisep.trackix.model.Mailing;
import com.juniorisep.trackix.model.Recipient;
import com.juniorisep.trackix.repository.MailingRepository;
import com.juniorisep.trackix.repository.RecipientsRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for the Mailing entity
 * <p>
 *     This class contains all the methods that can be applied to the Mailing entity
 *     This class is used to manage the mailings, to make the lists of mail recipients
 * </p>
 *
 * @see MailingRepository
 * @see Mailing
 * @see com.juniorisep.trackix.controller.MailingController
 *
 */

@Service
public class MailingService {

    private final MailingRepository mailingRepository;
    private final RecipientsRepository recipientRepository;

    public MailingService(MailingRepository mailingRepository, RecipientsRepository recipientRepository) {
        this.mailingRepository = mailingRepository;
        this.recipientRepository = recipientRepository;
    }

    public Object getAllMailings() {
        return mailingRepository.findAll();
    }


    public Object addMailing(MailingCreateRequest mailingDto) {

        Mailing mailing = Mailing.builder()
                .name(mailingDto.getName())
                .description(mailingDto.getDescription())

                .build();

        mailingRepository.save(mailing);

        //create all recipients in the database from the list of recipients in the dto
        for (Recipients recipient : mailingDto.getRecipients()) {
            Recipient recipientToSave = Recipient.builder()
                    .email(recipient.getEmail())
                    .firstName(recipient.getFirstName())
                    .lastName(recipient.getLastName())
                    .mailing(mailing)
                    .build();

            recipientRepository.save(recipientToSave);
        }

        return mailing;

    }

    public Object deleteMailing(int id) {

        return mailingRepository.findById(id)
                .map(mailing -> {
                    mailingRepository.delete(mailing);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new RuntimeException("Mailing not found!"));

    }

    public Object getMailingById(int id) {

        return mailingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mailing not found!"));
    }
}
