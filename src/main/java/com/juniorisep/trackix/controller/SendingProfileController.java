package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.SendingProfileCreateRequest;
import com.juniorisep.trackix.dto.SendingProfileUpdateRequest;
import com.juniorisep.trackix.service.SendingProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sending-profile")
public class SendingProfileController {

    private final SendingProfileService sendingProfileService;

    public SendingProfileController(SendingProfileService sendingProfileService) {
        this.sendingProfileService = sendingProfileService;
    }

    @GetMapping("/all")
    public Object getAllSendingProfiles() {
        return sendingProfileService.getAllSendingProfiles();
    }

    @PostMapping("/add")
    public Object addSendingProfile(@RequestBody SendingProfileCreateRequest sendingProfileDto) {
        return sendingProfileService.addSendingProfile(sendingProfileDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteSendingProfile(@PathVariable("id") int id) {
        return sendingProfileService.deleteSendingProfile(id);
    }

    @PutMapping("/update/{id}")
    public Object updateSendingProfile(@PathVariable("id") int id, @RequestBody SendingProfileUpdateRequest updateDto) {
        return sendingProfileService.updateSendingProfile(id, updateDto);
    }

}
