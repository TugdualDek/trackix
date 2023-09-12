package com.juniorisep.trackix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date createdDate;
    private Date launchDate;
    private Date completedDate;
    private int templateId;
    private Template template;
    private List<Group> groups;
    private String status;
    private int smtpId;
    private Smtp smtp;

}
