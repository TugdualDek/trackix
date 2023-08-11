package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.model.LinkTrack;
import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.service.DataLinkService;
import com.juniorisep.trackix.service.LinkService;
import com.juniorisep.trackix.service.MailService;
import com.juniorisep.trackix.service.DataMailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackerController {

    private final MailService mailService;
    private final DataMailService dataMailService;
    private final LinkService linkService;
    private final DataLinkService dataLinkService;

    public TrackerController(MailService mailService, DataMailService dataMailService, LinkService linkService, DataLinkService dataLinkService) {
        this.mailService = mailService;
        this.dataMailService = dataMailService;
        this.linkService = linkService;
        this.dataLinkService = dataLinkService;
    }

    @GetMapping("/mail/{id}.gif")
    public ResponseEntity<byte[]> trackCampaign(@PathVariable("id") int id, HttpServletRequest request) {

        Optional<MailTrack> mail = mailService.getCampaignById(id);

        //si la campagne existe
        if (mail.isPresent()) {

            //get the mailTrack object
            MailTrack mailTrack = mail.get();

            //check if mailTrack is not finished
            if (!mailTrack.isFinished()) {
                //get the user agent from the request
                String userAgent = extractUserAgent(request);
                System.out.println("User-Agent: " + userAgent);
                //get the client ip address from the request
                String clientIpAddress = extractClientIpAddress(request);
                System.out.println("Client IP Address: " + clientIpAddress);
                //get the remote host from the request
                String remoteHost = extractClientHostname(request);
                System.out.println("Remote Host: " + remoteHost);

                System.out.println(Arrays.toString(request.getCookies()));
                System.out.println(request.getRemoteUser());

                //save the data
                mailService.increaseCount(id);
                dataMailService.saveDataCampaign(userAgent, clientIpAddress, mailTrack);
            }

        }

        byte[] transparentGif = new byte[]{
                (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38, (byte) 0x39, (byte) 0x61,
                (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x80, (byte) 0x01,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x21,
                (byte) 0xF9, (byte) 0x04, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x2C, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x01, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x01,
                (byte) 0x01, (byte) 0x00, (byte) 0x3B
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_GIF);

        return ResponseEntity.ok()
                .headers(headers)
                .body(transparentGif);
    }

    @GetMapping("/redirect/{id}")
    public ResponseEntity<Void> redirectTo(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String redirectUrl = request.getHeader("referer");
        Optional<LinkTrack> link = linkService.getLinkById(id);

        if (link.isPresent()) {
            LinkTrack linkTrack = link.get();

            if (!linkTrack.isFinished()) {
                redirectUrl = linkTrack.getLinkRedirect();

                //get the user agent from the request
                String userAgent = extractUserAgent(request);
                System.out.println("User-Agent: " + userAgent);
                //get the client ip address from the request
                String clientIpAddress = extractClientIpAddress(request);
                System.out.println("Client IP Address: " + clientIpAddress);
                //get the remote host from the request
                String remoteHost = extractClientHostname(request);
                System.out.println("Remote Host: " + remoteHost);

                //save the data
                linkService.increaseCount(id);
                dataLinkService.saveDataLink(userAgent, clientIpAddress, linkTrack);
            }
        }

        // Set the "Location" header and return a redirect response
        response.setHeader("Location", redirectUrl);
        response.setStatus(HttpStatus.PERMANENT_REDIRECT.value());

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).build();
    }

    public String extractUserAgent(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.USER_AGENT);
    }

    public String extractClientIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public String extractClientHostname(HttpServletRequest request) {
        return request.getRemoteHost();
    }

}
