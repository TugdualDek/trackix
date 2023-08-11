package com.juniorisep.trackix.service;

import com.juniorisep.trackix.dto.LinkCreateRequest;
import com.juniorisep.trackix.model.LinkTrack;
import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final String LINK_URL = "http://137.74.196.178:8000";

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Optional<LinkTrack> getLinkById(int id) {
        return linkRepository.findById(id);
    }

    //method to increase the count of the link
    public void increaseCount(int id) {
        Optional<LinkTrack> linkTrack = linkRepository.findById(id);
        linkTrack.ifPresent(linkTrack1 -> {
            linkTrack1.setCount(linkTrack1.getCount() + 1);
            linkRepository.save(linkTrack1);
        });
    }

    public Object getAllLinks() {
        return linkRepository.findAll();
    }

    public Object getAllActiveLinks() {
        //get all links and remove the inactive ones
        List<LinkTrack> links = linkRepository.findAll();
        links.removeIf(LinkTrack::isFinished);
        return links;
    }

    public Object addLink(LinkCreateRequest linkDto) {

        //create a new link
        LinkTrack linkTrack = LinkTrack.builder()
                .name(linkDto.getName())
                .description(linkDto.getDescription())
                .startDate(new java.util.Date())
                .isFinished(false)
                .link("")
                .linkRedirect(linkDto.getLinkRedirect())
                .count(0)
                .dataLinks(null)
                .build();

        //save the link
        linkRepository.save(linkTrack);
        //generate the link
        generateLink(linkTrack.getId());

        return linkTrack;

    }

    public Object deleteLink(int id) {
        //delete the link and check if it has been deleted
        if (linkRepository.existsById(id)) {
            linkRepository.deleteById(id);
            return "Link deleted";
        } else {
            return "Link not found";
        }
    }

    public Object updateLink(int id, LinkCreateRequest linkDto) {

        Optional<LinkTrack> linkOptional = linkRepository.findById(id);

        //update the link and check if it has been updated
        if (linkOptional.isPresent()) {
            LinkTrack linkTrack = linkOptional.get();
            linkTrack.setName(linkDto.getName());
            linkTrack.setDescription(linkDto.getDescription());
            linkTrack.setLinkRedirect(linkDto.getLinkRedirect());
            linkRepository.save(linkTrack);
            return linkTrack;
        } else {
            return Optional.empty();
        }
    }

    public Object getLinkDataById(int id) {

        Optional<LinkTrack> linkOptional = linkRepository.findById(id);

        //get the link and check if it has been found
        if (linkOptional.isPresent()) {
            LinkTrack linkTrack = linkOptional.get();
            return linkTrack.getDataLinks();
        } else {
            return Optional.empty();
        }
    }

    public Object generateLink(int id) {
        Optional<LinkTrack> linkTrack = linkRepository.findById(id);
        linkTrack.ifPresent(mailTrack1 -> {
            mailTrack1.setLink(LINK_URL + "/track/redirect/" + mailTrack1.getId());
            linkRepository.save(mailTrack1);
        });
        return linkTrack;
    }
}
