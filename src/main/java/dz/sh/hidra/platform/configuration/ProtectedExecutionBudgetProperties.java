/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedExecutionBudgetProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-29
 *
 * @Type        : Record
 * @Layer       : Platform
 * @Module      : platform
 * @Package     : dz.sh.hidra.platform.configuration
 *
 * @Description : Defines bounded server-owned execution budgets independently of authorization expiry.
 *
 */
package dz.sh.hidra.platform.configuration;

import org.springframework.core.env.Environment;

/** Finite server-owned work caps; no extension of independently attested authority validity. */
public record ProtectedExecutionBudgetProperties(long appointmentWorkBudgetMillis,long custodyWorkBudgetMillis,long installedUnitWorkBudgetMillis) {
    public static final long STANDARD_WORK_BUDGET_MILLIS=500;
    // Development software work cap, not an HTTP latency target or industrial operating SLA.
    public static final long DEFAULT_APPOINTMENT_WORK_BUDGET_MILLIS=7_500;
    // Provisional development headroom for observed Custody overruns; not an operational SLA.
    public static final long DEFAULT_CUSTODY_WORK_BUDGET_MILLIS=1_000;
    public static final String CUSTODY_PROPERTY=
            "hidra.platform.persistence.execution.custody-work-budget-ms";
    // Provisional headroom for measured installed-unit admission/relocation work; not an operational SLA.
    public static final long DEFAULT_INSTALLED_UNIT_WORK_BUDGET_MILLIS=1_000;
    public static final String INSTALLED_UNIT_PROPERTY=
            "hidra.platform.persistence.execution.installed-unit-work-budget-ms";
    // Finite development qualification ceiling, not an accepted operational latency target.
    public static final long MAX_WORK_BUDGET_MILLIS=10_000;
    public static final String APPOINTMENT_PROPERTY=
            "hidra.platform.persistence.execution.appointment-work-budget-ms";

    public ProtectedExecutionBudgetProperties(long appointmentWorkBudgetMillis) {
        this(appointmentWorkBudgetMillis,DEFAULT_CUSTODY_WORK_BUDGET_MILLIS);
    }
    public ProtectedExecutionBudgetProperties(long appointmentWorkBudgetMillis,long custodyWorkBudgetMillis) {
        this(appointmentWorkBudgetMillis,custodyWorkBudgetMillis,DEFAULT_INSTALLED_UNIT_WORK_BUDGET_MILLIS);
    }
    public ProtectedExecutionBudgetProperties {
        if(appointmentWorkBudgetMillis<1 || appointmentWorkBudgetMillis>MAX_WORK_BUDGET_MILLIS)
            throw new IllegalArgumentException("Protected appointment work budget must be between 1 and 10000 milliseconds");
        if(custodyWorkBudgetMillis<1 || custodyWorkBudgetMillis>MAX_WORK_BUDGET_MILLIS)
            throw new IllegalArgumentException("Protected custody work budget must be between 1 and 10000 milliseconds");
        if(installedUnitWorkBudgetMillis<1 || installedUnitWorkBudgetMillis>MAX_WORK_BUDGET_MILLIS)
            throw new IllegalArgumentException("Protected installed-unit work budget must be between 1 and 10000 milliseconds");
    }
    public static ProtectedExecutionBudgetProperties defaults() {
        return new ProtectedExecutionBudgetProperties(DEFAULT_APPOINTMENT_WORK_BUDGET_MILLIS);
    }
    public static ProtectedExecutionBudgetProperties from(Environment environment) {
        return new ProtectedExecutionBudgetProperties(environment.getProperty(
                APPOINTMENT_PROPERTY,Long.class,DEFAULT_APPOINTMENT_WORK_BUDGET_MILLIS),
                environment.getProperty(CUSTODY_PROPERTY,Long.class,DEFAULT_CUSTODY_WORK_BUDGET_MILLIS),
                environment.getProperty(INSTALLED_UNIT_PROPERTY,Long.class,DEFAULT_INSTALLED_UNIT_WORK_BUDGET_MILLIS));
    }
    public long installedUnitWorkBudgetNanos() { return installedUnitWorkBudgetMillis*1_000_000L; }
    public long custodyWorkBudgetNanos() { return custodyWorkBudgetMillis*1_000_000L; }
    public long appointmentWorkBudgetNanos() { return appointmentWorkBudgetMillis*1_000_000L; }
}
