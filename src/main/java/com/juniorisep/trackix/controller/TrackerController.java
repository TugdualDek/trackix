package com.juniorisep.trackix.controller;

import com.juniorisep.trackix.dto.MailRequest;
import com.juniorisep.trackix.model.LinkTrack;
import com.juniorisep.trackix.model.MailTrack;
import com.juniorisep.trackix.service.DataLinkService;
import com.juniorisep.trackix.service.LinkService;
import com.juniorisep.trackix.service.MailService;
import com.juniorisep.trackix.service.DataMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/track")
public class TrackerController {

    private final MailService mailService;
    private final DataMailService dataMailService;
    private final LinkService linkService;
    private final DataLinkService dataLinkService;

    @Autowired
    private JavaMailSender javaMailSender;

    public TrackerController(MailService mailService, DataMailService dataMailService, LinkService linkService, DataLinkService dataLinkService) {
        this.mailService = mailService;
        this.dataMailService = dataMailService;
        this.linkService = linkService;
        this.dataLinkService = dataLinkService;
    }

    @GetMapping("/mail/{id}/image.png")
    public ResponseEntity<byte[]> trackCampaign(@PathVariable("id") int id, HttpServletRequest request) {

        Optional<MailTrack> mail = mailService.getCampaignById(id);

        // check if the mail track exists
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

        byte[] transparentPng = new byte[] {
                (byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x48, (byte) 0x44, (byte) 0x52,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
                (byte) 0x08, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x1F, (byte) 0x15, (byte) 0xC4,
                (byte) 0x89, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x0D, (byte) 0x49, (byte) 0x44, (byte) 0x41,
                (byte) 0x54, (byte) 0x78, (byte) 0x9C, (byte) 0x63, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00,
                (byte) 0x05, (byte) 0x00, (byte) 0x01, (byte) 0x0D, (byte) 0x0A, (byte) 0x2D, (byte) 0xB4, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x49, (byte) 0x45, (byte) 0x4E, (byte) 0x44, (byte) 0xAE,
                (byte) 0x42, (byte) 0x60, (byte) 0x82
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return ResponseEntity.ok()
                .headers(headers)
                .body(transparentPng);
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

    @PostMapping("/send")
    public void sendMail(@RequestBody MailRequest mailDto) throws MessagingException, UnsupportedEncodingException {
        sendHtmlMessage(mailDto.getTo(), mailDto.getFrom(), mailDto.getBody(), mailDto.getFrom());
    }

    public void sendHtmlMessage(String to, String subject, String htmlBody, String from) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(new InternetAddress("spoofed-email@example.com", "Spoofed Sender Name"));
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.setReplyTo(new InternetAddress("spoofed-email@example.com", "Spoofed Sender Name"));
        javaMailSender.send(message);
    }

}
