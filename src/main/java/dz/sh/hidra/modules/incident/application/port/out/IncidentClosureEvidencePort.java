/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentClosureEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.Incident;
public interface IncidentClosureEvidencePort {
    record Evidence(boolean hasResolution,boolean hasEvidence,boolean evidenceRequired,boolean approvalRequired,
            boolean rootCauseRequired,boolean rootCausePresent,boolean followUpRequired,boolean followUpPresent) {}
    Evidence inspect(Incident incident);
}
