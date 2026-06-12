/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAddressJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for EmployeeAddress.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import dz.sh.hidra.modules.organization.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for EmployeeAddress.
     */
    @Entity
    @Table(name = "hidra_org_employee_address")
    public class EmployeeAddressJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "employee_id", nullable = false, length = 80)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 80)
    private AddressType addressType;

    @Column(name = "locality_id", nullable = false, length = 80)
    private String localityId;

    @Column(name = "street_line1", nullable = true, length = 255)
    private String streetLine1;

    @Column(name = "street_line2", nullable = true, length = 255)
    private String streetLine2;

    @Column(name = "postal_code_snapshot", nullable = true, length = 40)
    private String postalCodeSnapshot;

    @Column(name = "primary_address", nullable = false)
    private boolean primaryAddress;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected EmployeeAddressJpaEntity() {
            // Required by JPA.
        }

        public EmployeeAddressJpaEntity(
                String id,
            String employeeId,
            AddressType addressType,
            String localityId,
            String streetLine1,
            String streetLine2,
            String postalCodeSnapshot,
            boolean primaryAddress,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.employeeId = employeeId;
        this.addressType = addressType;
        this.localityId = localityId;
        this.streetLine1 = streetLine1;
        this.streetLine2 = streetLine2;
        this.postalCodeSnapshot = postalCodeSnapshot;
        this.primaryAddress = primaryAddress;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String employeeId() {
        return employeeId;
    }


    public AddressType addressType() {
        return addressType;
    }


    public String localityId() {
        return localityId;
    }


    public String streetLine1() {
        return streetLine1;
    }


    public String streetLine2() {
        return streetLine2;
    }


    public String postalCodeSnapshot() {
        return postalCodeSnapshot;
    }


    public boolean primaryAddress() {
        return primaryAddress;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
