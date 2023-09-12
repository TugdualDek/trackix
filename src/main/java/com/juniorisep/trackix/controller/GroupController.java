package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.GroupCreateRequest;
import com.juniorisep.trackix.dto.Target;
import com.juniorisep.trackix.service.GroupService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mailing")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public Object getAllMailings() {
        return groupService.getAllMailings();
    }

    @PostMapping("/add")
    public Object addMailing(@RequestBody GroupCreateRequest mailingDto) {
        return groupService.addMailing(mailingDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteMailing(@PathVariable("id") int id) {
        return groupService.deleteMailing(id);
    }

/*    @PutMapping("/update/{id}")
    public Object updateMailing(@PathVariable("id") int id, @RequestBody MailingCreateRequest updateDto) {
        return mailingService.updateMailing(id, updateDto);
    }*/

    @GetMapping("/get/{id}")
    public Object getMailingById(@PathVariable("id") int id) {
        return groupService.getMailingById(id);
    }

    @PostMapping("/add/{id}/recipient")
    public Object addRecipient(@RequestBody Target recipientDto, @PathVariable("id") int id) {
        return groupService.addRecipient(recipientDto, id);
    }

}
