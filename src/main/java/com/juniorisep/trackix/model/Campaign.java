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
    @JoinColumn(name = "template_id") // Relation many-to-one avec Template
    @JsonBackReference // Évitez la boucle de sérialisation
    private Template template;

    @ManyToMany
    @JoinTable(
            name = "campaign_group",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    @JsonBackReference // Évitez la boucle de sérialisation
    private List<Group> groups;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    @JsonBackReference // Évitez la boucle de sérialisation
    private List<CampaignResults> results;

    private String status;

    @ManyToOne
    @JoinColumn(name = "smtp_id") // Relation many-to-one avec Smtp
    @JsonBackReference // Évitez la boucle de sérialisation
    private Smtp smtp;
}
