/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an organization employee.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * JPA representation of an organization employee.
 *
 * <p>Business role:
 * This entity stores a real operational employee and the persistence-side copies of assignments
 * and reporting lines. It does not represent an identity user, role, permission, or topology asset.
 *
 * <p>Architecture role:
 * This class belongs to the organization infrastructure persistence layer. It must not be returned
 * by application ports or REST controllers.
 *
 * <p>Validation:
 * Domain validation is handled by domain value objects, aggregates, policies, and services before
 * mapping to this persistence entity. Database constraints provide persistence-level protection.
 *
 * <p>Usage:
 * Use only inside organization persistence repositories, adapters, and persistence mappers.
 */
@Entity
@Table(name = "hidra_org_employee")
public class EmployeeJpaEntity {

    /** Stable employee identifier. */
    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    /** Unique business employee number. */
    @Column(name = "employee_number", nullable = false, unique = true, length = 40)
    private String employeeNumber;

    /** Employee full name. */
    @Column(name = "full_name", nullable = false, length = 160)
    private String fullName;

    /** Optional professional email. */
    @Column(name = "email", length = 120)
    private String email;

    /** Employee lifecycle status. */
    @Column(name = "status", nullable = false, length = 40)
    private String status;

    /** Optional neutral identity user reference. */
    @Column(name = "identity_user_reference", length = 120)
    private String identityUserReference;

    /** Employee assignments persisted with the employee aggregate. */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeeAssignmentJpaEntity> assignments = new ArrayList<>();

    /** Employee reporting lines persisted with the employee aggregate. */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportingLineJpaEntity> reportingLines = new ArrayList<>();

    /** Creation instant. */
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Optional activation instant. */
    @Column(name = "activated_at")
    private Instant activatedAt;

    /** Optional suspension instant. */
    @Column(name = "suspended_at")
    private Instant suspendedAt;

    /** Optional disabled instant. */
    @Column(name = "disabled_at")
    private Instant disabledAt;

    /** Last update instant. */
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected EmployeeJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentityUserReference() {
        return identityUserReference;
    }

    public void setIdentityUserReference(String identityUserReference) {
        this.identityUserReference = identityUserReference;
    }

    public List<EmployeeAssignmentJpaEntity> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<EmployeeAssignmentJpaEntity> assignments) {
        this.assignments.clear();
        if (assignments != null) {
            assignments.forEach(this::addAssignment);
        }
    }

    public void addAssignment(EmployeeAssignmentJpaEntity assignment) {
        assignments.add(assignment);
        assignment.setEmployee(this);
    }

    public List<ReportingLineJpaEntity> getReportingLines() {
        return reportingLines;
    }

    public void setReportingLines(List<ReportingLineJpaEntity> reportingLines) {
        this.reportingLines.clear();
        if (reportingLines != null) {
            reportingLines.forEach(this::addReportingLine);
        }
    }

    public void addReportingLine(ReportingLineJpaEntity reportingLine) {
        reportingLines.add(reportingLine);
        reportingLine.setEmployee(this);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getActivatedAt() {
        return activatedAt;
    }

    public void setActivatedAt(Instant activatedAt) {
        this.activatedAt = activatedAt;
    }

    public Instant getSuspendedAt() {
        return suspendedAt;
    }

    public void setSuspendedAt(Instant suspendedAt) {
        this.suspendedAt = suspendedAt;
    }

    public Instant getDisabledAt() {
        return disabledAt;
    }

    public void setDisabledAt(Instant disabledAt) {
        this.disabledAt = disabledAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
