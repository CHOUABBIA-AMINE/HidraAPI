/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyTicketLineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CustodyTicketLine.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for CustodyTicketLine.
     */
    @Entity
    @Table(name = "hidra_custody_ticket_line")
    public class CustodyTicketLineJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "transfer_ticket_id", nullable = false, length = 80)
    private String transferTicketId;

    @Column(name = "line_number", nullable = false)
    private int lineNumber;

    @Column(name = "line_type_id", nullable = false, length = 80)
    private String lineTypeId;

    @Column(name = "product_type_id", nullable = false, length = 80)
    private String productTypeId;

    @Column(name = "quantity", nullable = false, precision = 18, scale = 6)
    private BigDecimal quantity;

    @Column(name = "quantity_unit_id", nullable = false, length = 80)
    private String quantityUnitId;

    @Column(name = "quality_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal qualityValue;

    @Column(name = "quality_unit_id", nullable = true, length = 80)
    private String qualityUnitId;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected CustodyTicketLineJpaEntity() {
            // Required by JPA.
        }

        public CustodyTicketLineJpaEntity(
                String id,
            String transferTicketId,
            int lineNumber,
            String lineTypeId,
            String productTypeId,
            BigDecimal quantity,
            String quantityUnitId,
            BigDecimal qualityValue,
            String qualityUnitId,
            String description,
            Instant createdAt
        ) {
            this.id = id;
        this.transferTicketId = transferTicketId;
        this.lineNumber = lineNumber;
        this.lineTypeId = lineTypeId;
        this.productTypeId = productTypeId;
        this.quantity = quantity;
        this.quantityUnitId = quantityUnitId;
        this.qualityValue = qualityValue;
        this.qualityUnitId = qualityUnitId;
        this.description = description;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String transferTicketId() {
        return transferTicketId;
    }


    public int lineNumber() {
        return lineNumber;
    }


    public String lineTypeId() {
        return lineTypeId;
    }


    public String productTypeId() {
        return productTypeId;
    }


    public BigDecimal quantity() {
        return quantity;
    }


    public String quantityUnitId() {
        return quantityUnitId;
    }


    public BigDecimal qualityValue() {
        return qualityValue;
    }


    public String qualityUnitId() {
        return qualityUnitId;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
