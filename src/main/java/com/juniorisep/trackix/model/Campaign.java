package com.juniorisep.trackix.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Date createdDate;

    private Date launchDate;

    private Date completedDate;

    @ManyToOne
    @JoinColumn(name = "template_id")
    @JsonBackReference
    private Template template;

    @ManyToMany
    @JoinTable(
            name = "campaign_group",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonBackReference
    private List<Group> groups;

    private String status;

    @ManyToOne
    @JoinColumn(name = "smtp_id")
    @JsonBackReference
    private Smtp smtp;

    @OneToOne(mappedBy = "campaign", cascade = CascadeType.ALL)
    @JoinColumn(name = "mail_track_id") // Ajouter une colonne mail_track_id pour la relation One-to-One
    @JsonBackReference
    private MailTrack mailTrack;
}
