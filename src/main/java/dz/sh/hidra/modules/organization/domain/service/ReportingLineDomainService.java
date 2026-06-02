/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingLineDomainService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.service
 *
 * @Description : Domain service coordinating reporting line validation and matrix reporting rules.
 *
 */
package dz.sh.hidra.modules.organization.domain.service;

import java.time.LocalDate;
import java.util.Objects;

import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.ReportingLine;
import dz.sh.hidra.modules.organization.domain.policy.ReportingLinePolicy;

/**
 * Coordinates reporting line validation and matrix reporting rules.
 *
 * <p>Business role:
 * This service validates employee-to-manager relationships in the operational organization,
 * including primary LINE reporting and functional, administrative, technical, operational, or
 * dotted-line matrix reporting.
 *
 * <p>Architecture role:
 * This is a pure organization domain service. It coordinates domain models and policies without
 * depending on Spring, JPA, REST DTOs, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * It delegates self-reporting, manager status, employee status, uniqueness, and matrix reporting
 * checks to <code>ReportingLinePolicy</code>.
 *
 * <p>Usage:
 * Use this service when adding or changing reporting lines between employee aggregates.
 */
public final class ReportingLineDomainService {

    /**
     * Policy protecting reporting line invariants.
     */
    private final ReportingLinePolicy reportingLinePolicy;

    /**
     * Creates the reporting line domain service.
     *
     * @param reportingLinePolicy reporting line policy
     */
    public ReportingLineDomainService(ReportingLinePolicy reportingLinePolicy) {
        this.reportingLinePolicy = Objects.requireNonNull(reportingLinePolicy, "Reporting line policy must not be null.");
    }

    /**
     * Adds a reporting line after validating matrix reporting rules.
     *
     * @param employee employee receiving the reporting line
     * @param manager employee acting as manager
     * @param reportingLine reporting line to add
     * @param evaluationDate date used for active-line checks
     * @return updated employee aggregate
     */
    public Employee addReportingLine(
            Employee employee,
            Employee manager,
            ReportingLine reportingLine,
            LocalDate evaluationDate) {

        reportingLinePolicy.ensureReportingLineAllowed(
                employee,
                manager,
                reportingLine,
                employee.reportingLines(),
                evaluationDate);

        return employee.addReportingLine(reportingLine);
    }
}
