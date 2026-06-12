/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEvidenceLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationEvidenceLink.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import dz.sh.hidra.modules.simulation.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for SimulationEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_simulation_evidence_link")
    public class SimulationEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_type", nullable = false, length = 40)
    private SimulationOwnerType ownerType;

    @Column(name = "owner_id", nullable = false, length = 80)
    private String ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 40)
    private SimulationEvidenceType evidenceType;

    @Column(name = "evidence_reference", nullable = false, length = 255)
    private String evidenceReference;

    @Column(name = "label", nullable = true, length = 240)
    private String label;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public SimulationEvidenceLinkJpaEntity(
                String id,
            SimulationOwnerType ownerType,
            String ownerId,
            SimulationEvidenceType evidenceType,
            String evidenceReference,
            String label,
            Instant createdAt
        ) {
            this.id = id;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.evidenceType = evidenceType;
        this.evidenceReference = evidenceReference;
        this.label = label;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public SimulationOwnerType ownerType() {
        return ownerType;
    }


    public String ownerId() {
        return ownerId;
    }


    public SimulationEvidenceType evidenceType() {
        return evidenceType;
    }


    public String evidenceReference() {
        return evidenceReference;
    }


    public String label() {
        return label;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
