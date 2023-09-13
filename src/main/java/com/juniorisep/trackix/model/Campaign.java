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
    @JoinColumn(name = "template_id") // Relation many-to-one avec Template
    private Template template;

    @ManyToMany
    @JoinTable(
            name = "campaign_group",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<CampaignResults> results;

    private String status;

    @ManyToOne
    @JoinColumn(name = "smtp_id") // Relation many-to-one avec Smtp
    private Smtp smtp;
}
