package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.LinkCreateRequest;
import com.juniorisep.trackix.service.LinkService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/all")
    public Object getAllLinks() {
        return linkService.getAllLinks();
    }

    @GetMapping("/all/active")
    public Object getAllActiveLinks() {
        return linkService.getAllActiveLinks();
    }

    @PostMapping("/add")
    public Object addLink(@RequestBody LinkCreateRequest linkDto) {
        return linkService.addLink(linkDto);
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteLink(@PathVariable("id") int id) {
        return linkService.deleteLink(id);
    }

    @PutMapping("/update/{id}")
    public Object updateLink(@PathVariable("id") int id, @RequestBody LinkCreateRequest updateDto) {
        return linkService.updateLink(id, updateDto);
    }

    @GetMapping("/get/{id}")
    public Object getLinkById(@PathVariable("id") int id) {
        return linkService.getLinkById(id);
    }

    @GetMapping("/get/{id}/data")
    public Object getLinkDataById(@PathVariable("id") int id) {
        return linkService.getLinkDataById(id);
    }

    @GetMapping("/{id}/generate")
    public Object generateLink(@PathVariable("id") int id) {
        return linkService.generateLink(id);
    }
}
