package com.juniorisep.trackix.service;

import com.juniorisep.trackix.controller.GroupController;
import com.juniorisep.trackix.dto.GroupCreateRequest;
import com.juniorisep.trackix.dto.TargetRequest;
import com.juniorisep.trackix.model.Group;
import com.juniorisep.trackix.model.Target;
import com.juniorisep.trackix.repository.TargetRepository;
import com.juniorisep.trackix.repository.GroupRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for the Group entity
 * <p>
 *     This class contains all the methods that can be applied to the Group entity
 *     This class is used to manage the groups, to make the lists of mail recipients
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

        groupRepository.save(group);

        //create all recipients in the database from the list of recipients in the dto
        for (TargetRequest recipient : mailingDto.getRecipients()) {
            com.juniorisep.trackix.model.Target targetToSave = com.juniorisep.trackix.model.Target.builder()
                    .email(recipient.getEmail())
                    .firstName(recipient.getFirstName())
                    .lastName(recipient.getLastName())
                    .group(group)
                    .build();

            recipientRepository.save(targetToSave);
        }

        return group;

    }

    public Object addRecipient(TargetRequest recipientDto, int id) {

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found!"));

        Target targetToSave = Target.builder()
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
                }).orElseThrow(() -> new RuntimeException("Group not found!"));

    }

    public Object getMailingById(int id) {

        return groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found!"));
    }
}
