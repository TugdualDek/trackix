package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.MailingCreateRequest;
import com.juniorisep.trackix.dto.Recipients;
import com.juniorisep.trackix.service.MailingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mailing")
public class MailingController {

    private final MailingService mailingService;

    public MailingController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @GetMapping("/all")
    public Object getAllMailings() {
        return mailingService.getAllMailings();
    }

    @PostMapping("/add")
    public Object addMailing(@RequestBody MailingCreateRequest mailingDto) {
        return mailingService.addMailing(mailingDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteMailing(@PathVariable("id") int id) {
        return mailingService.deleteMailing(id);
    }

/*    @PutMapping("/update/{id}")
    public Object updateMailing(@PathVariable("id") int id, @RequestBody MailingCreateRequest updateDto) {
        return mailingService.updateMailing(id, updateDto);
    }*/

    @GetMapping("/get/{id}")
    public Object getMailingById(@PathVariable("id") int id) {
        return mailingService.getMailingById(id);
    }

    @PostMapping("/add/{id}/recipient")
    public Object addRecipient(@RequestBody Recipients recipientDto, @PathVariable("id") int id) {
        return mailingService.addRecipient(recipientDto, id);
    }

}
