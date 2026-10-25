/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedExecutionBudgetProperties
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
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

/** Defaults preserve the accepted shared limit; wider appointment values require qualification. */
public record ProtectedExecutionBudgetProperties(long appointmentWorkBudgetMillis) {
    public static final long STANDARD_WORK_BUDGET_MILLIS=500;
    // Finite development qualification ceiling, not an accepted operational latency target.
    public static final long MAX_WORK_BUDGET_MILLIS=10_000;
    public static final String APPOINTMENT_PROPERTY=
            "hidra.platform.persistence.execution.appointment-work-budget-ms";

    public ProtectedExecutionBudgetProperties {
        if(appointmentWorkBudgetMillis<1 || appointmentWorkBudgetMillis>MAX_WORK_BUDGET_MILLIS)
            throw new IllegalArgumentException("Protected appointment work budget must be between 1 and 10000 milliseconds");
    }
    public static ProtectedExecutionBudgetProperties defaults() {
        return new ProtectedExecutionBudgetProperties(STANDARD_WORK_BUDGET_MILLIS);
    }
    public static ProtectedExecutionBudgetProperties from(Environment environment) {
        return new ProtectedExecutionBudgetProperties(environment.getProperty(
                APPOINTMENT_PROPERTY,Long.class,STANDARD_WORK_BUDGET_MILLIS));
    }
    public long appointmentWorkBudgetNanos() { return appointmentWorkBudgetMillis*1_000_000L; }
}
