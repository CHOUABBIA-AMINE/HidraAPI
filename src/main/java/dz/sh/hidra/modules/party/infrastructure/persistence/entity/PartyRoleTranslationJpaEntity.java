/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleTranslationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PartyRoleTranslation.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PartyRoleTranslation.
     */
    @Entity
    @Table(name = "hidra_party_role_translation")
    public class PartyRoleTranslationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_role_id", nullable = false, length = 80)
    private String partyRoleId;

    @Column(name = "language_code", nullable = false, length = 10)
    private String languageCode;

    @Column(name = "label", nullable = false, length = 255)
    private String label;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PartyRoleTranslationJpaEntity() {
            // Required by JPA.
        }

        public PartyRoleTranslationJpaEntity(
                String id,
            String partyRoleId,
            String languageCode,
            String label,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyRoleId = partyRoleId;
        this.languageCode = languageCode;
        this.label = label;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyRoleId() {
        return partyRoleId;
    }


    public String languageCode() {
        return languageCode;
    }


    public String label() {
        return label;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
