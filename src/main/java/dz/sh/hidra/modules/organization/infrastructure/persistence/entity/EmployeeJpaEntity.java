/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Employee.
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
     * Database-backed JPA entity for Employee.
     */
    @Entity
    @Table(name = "hidra_org_employee")
    public class EmployeeJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "employee_number", nullable = false, length = 120)
    private String employeeNumber;

    @Column(name = "first_name_ar", nullable = true, length = 255)
    private String firstNameAr;

    @Column(name = "last_name_ar", nullable = true, length = 255)
    private String lastNameAr;

    @Column(name = "first_name_lt", nullable = true, length = 255)
    private String firstNameLt;

    @Column(name = "last_name_lt", nullable = true, length = 255)
    private String lastNameLt;

    @Column(name = "display_name_ar", nullable = true, length = 255)
    private String displayNameAr;

    @Column(name = "display_name_lt", nullable = true, length = 255)
    private String displayNameLt;

    @Column(name = "email_address", nullable = true, length = 254)
    private String emailAddress;

    @Column(name = "mobile_number", nullable = true, length = 80)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false, length = 80)
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private EmployeeStatus status;

    @Column(name = "identity_user_reference", nullable = true, length = 120)
    private String identityUserReference;

    @Column(name = "hired_at", nullable = true)
    private Instant hiredAt;

    @Column(name = "terminated_at", nullable = true)
    private Instant terminatedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected EmployeeJpaEntity() {
            // Required by JPA.
        }

        public EmployeeJpaEntity(
                String id,
            String employeeNumber,
            String firstNameAr,
            String lastNameAr,
            String firstNameLt,
            String lastNameLt,
            String displayNameAr,
            String displayNameLt,
            String emailAddress,
            String mobileNumber,
            EmployeeType employeeType,
            EmployeeStatus status,
            String identityUserReference,
            Instant hiredAt,
            Instant terminatedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.employeeNumber = employeeNumber;
        this.firstNameAr = firstNameAr;
        this.lastNameAr = lastNameAr;
        this.firstNameLt = firstNameLt;
        this.lastNameLt = lastNameLt;
        this.displayNameAr = displayNameAr;
        this.displayNameLt = displayNameLt;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.employeeType = employeeType;
        this.status = status;
        this.identityUserReference = identityUserReference;
        this.hiredAt = hiredAt;
        this.terminatedAt = terminatedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String employeeNumber() {
        return employeeNumber;
    }


    public String firstNameAr() {
        return firstNameAr;
    }


    public String lastNameAr() {
        return lastNameAr;
    }


    public String firstNameLt() {
        return firstNameLt;
    }


    public String lastNameLt() {
        return lastNameLt;
    }


    public String displayNameAr() {
        return displayNameAr;
    }


    public String displayNameLt() {
        return displayNameLt;
    }


    public String emailAddress() {
        return emailAddress;
    }


    public String mobileNumber() {
        return mobileNumber;
    }


    public EmployeeType employeeType() {
        return employeeType;
    }


    public EmployeeStatus status() {
        return status;
    }


    public String identityUserReference() {
        return identityUserReference;
    }


    public Instant hiredAt() {
        return hiredAt;
    }


    public Instant terminatedAt() {
        return terminatedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
