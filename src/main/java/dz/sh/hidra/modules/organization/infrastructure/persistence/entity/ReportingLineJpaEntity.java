/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence.entity
 *
 * @Description : JPA representation of a matrix reporting line.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * JPA representation of a matrix reporting line.
 *
 * <p>Business role:
 * This entity stores employee-to-manager reporting relationships, including LINE, OPERATIONAL,
 * FUNCTIONAL, ADMINISTRATIVE, TECHNICAL, and DOTTED_LINE reporting.
 *
 * <p>Architecture role:
 * This persistence entity belongs to infrastructure and must not leak into application or API
 * contracts.
 *
 * <p>Validation:
 * Domain reporting-line policies validate self-reporting and primary-line rules before
 * persistence. Database constraints protect required columns.
 *
 * <p>Usage:
 * Use only through EmployeeJpaEntity and persistence mappers.
 */
@Entity
@Table(name = "hidra_org_reporting_line")
public class ReportingLineJpaEntity {

    /** Stable reporting line identifier. */
    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    /** Owning employee persistence entity. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeJpaEntity employee;

    /** Copied employee id for simple query and mapping support. */
    @Column(name = "employee_id", nullable = false, insertable = false, updatable = false, length = 80)
    private String employeeId;

    /** Manager employee identifier. */
    @Column(name = "manager_employee_id", nullable = false, length = 80)
    private String managerEmployeeId;

    /** Reporting line type. */
    @Column(name = "reporting_line_type", nullable = false, length = 60)
    private String reportingLineType;

    /** Whether this is the primary reporting line. */
    @Column(name = "primary_line", nullable = false)
    private boolean primaryLine;

    /** Reporting line effective start date. */
    @Column(name = "effective_from", nullable = false)
    private LocalDate effectiveFrom;

    /** Optional reporting line effective end date. */
    @Column(name = "effective_to")
    private LocalDate effectiveTo;

    /** Optional reporting line description. */
    @Column(name = "description", length = 500)
    private String description;

    protected ReportingLineJpaEntity() {
        // Required by JPA.
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EmployeeJpaEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeJpaEntity employee) {
        this.employee = employee;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getManagerEmployeeId() {
        return managerEmployeeId;
    }

    public void setManagerEmployeeId(String managerEmployeeId) {
        this.managerEmployeeId = managerEmployeeId;
    }

    public String getReportingLineType() {
        return reportingLineType;
    }

    public void setReportingLineType(String reportingLineType) {
        this.reportingLineType = reportingLineType;
    }

    public boolean isPrimaryLine() {
        return primaryLine;
    }

    public void setPrimaryLine(boolean primaryLine) {
        this.primaryLine = primaryLine;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDate getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDate effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
