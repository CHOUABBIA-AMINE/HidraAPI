/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentLifecycleGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.domain.service
 *
 * @Description : Guards incident lifecycle transitions.
 *
 */
package dz.sh.hidra.modules.incident.domain.service;

import dz.sh.hidra.modules.incident.domain.exception.IncidentLifecycleViolationException;
import dz.sh.hidra.modules.incident.domain.model.Incident;
import dz.sh.hidra.modules.incident.domain.value.IncidentStatus;

/**
 * Guards incident lifecycle transitions.
 */
public class IncidentLifecycleGuard {

    public void ensureResponseActionAllowed(Incident incident) {
        if (incident == null) {
            throw new IncidentLifecycleViolationException("Incident must not be null.");
        }
        if (!incident.canReceiveResponseAction()) {
            throw new IncidentLifecycleViolationException("Response actions cannot be added to draft or closed incidents.");
        }
    }

    public void ensureCanClose(Incident incident, boolean hasResolution, boolean hasEvidenceReviewed) {
        if (incident == null) {
            throw new IncidentLifecycleViolationException("Incident must not be null.");
        }
        if (incident.status() != IncidentStatus.RESOLVED) {
            throw new IncidentLifecycleViolationException("Only resolved incidents may be closed.");
        }
        if (!hasResolution || !hasEvidenceReviewed) {
            throw new IncidentLifecycleViolationException("Closure requires resolution and evidence review.");
        }
    }
}
