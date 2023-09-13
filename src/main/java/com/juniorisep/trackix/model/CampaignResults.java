package com.juniorisep.trackix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "campaign_results")
public class CampaignResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign; // Relation many-to-one avec Campaign

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target; // Relation many-to-one avec Target

    private Date openDate; // Date d'ouverture du mail
}
