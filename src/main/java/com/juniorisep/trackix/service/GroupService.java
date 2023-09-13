package com.juniorisep.trackix.service;

import com.juniorisep.trackix.controller.GroupController;
import com.juniorisep.trackix.dto.GroupCreateRequest;
import com.juniorisep.trackix.dto.Target;
import com.juniorisep.trackix.model.Group;
import com.juniorisep.trackix.repository.TargetRepository;
import com.juniorisep.trackix.repository.GroupRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for the Mailing entity
 * <p>
 *     This class contains all the methods that can be applied to the Mailing entity
 *     This class is used to manage the mailings, to make the lists of mail recipients
 * </p>
 *
 * @see GroupRepository
 * @see Group
 * @see GroupController
 *
 */

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final TargetRepository recipientRepository;

    public GroupService(GroupRepository groupRepository, TargetRepository recipientRepository) {
        this.groupRepository = groupRepository;
        this.recipientRepository = recipientRepository;
    }

    public Object getAllMailings() {
        return groupRepository.findAll();
    }


    public Object addMailing(GroupCreateRequest mailingDto) {

        Group group = Group.builder()
                .name(mailingDto.getName())
                .description(mailingDto.getDescription())
                .build();

        //create all recipients in the database from the list of recipients in the dto
        for (Target recipient : mailingDto.getRecipients()) {
            com.juniorisep.trackix.model.Target targetToSave = com.juniorisep.trackix.model.Target.builder()
                    .email(recipient.getEmail())
                    .firstName(recipient.getFirstName())
                    .lastName(recipient.getLastName())
                    .group(group)
                    .build();

            recipientRepository.save(targetToSave);
        }

        groupRepository.save(group);

        return group;

    }

    public Object addRecipient(Target recipientDto, int id) {

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mailing not found!"));

        com.juniorisep.trackix.model.Target targetToSave = com.juniorisep.trackix.model.Target.builder()
                .email(recipientDto.getEmail())
                .firstName(recipientDto.getFirstName())
                .lastName(recipientDto.getLastName())
                .group(group)
                .build();

        recipientRepository.save(targetToSave);

        return targetToSave;

    }

    public Object deleteMailing(int id) {

        return groupRepository.findById(id)
                .map(group -> {
                    groupRepository.delete(group);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new RuntimeException("Mailing not found!"));

    }

    public Object getMailingById(int id) {

        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mailing not found!"));
    }
}
