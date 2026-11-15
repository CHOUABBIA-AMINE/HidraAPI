/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceApprovalGuard
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.domain.service
 * @Description : T18 governed Integrity and Assets maintenance handoff boundary.
 */
package dz.sh.hidra.modules.integrity.domain.service;

import java.util.Objects;

/** Pure business validation. Persistence and Identity checks remain outside Domain. */
public final class IntegrityMaintenanceApprovalGuard {
    public void requireEligible(String runStatus, String recommendationStatus,
            String caseStatus, String findingDefect, String recommendationDefect,
            String caseDefect, String defectId, String findingId, String defectFinding,
            String runType, String runId, String findingType, String findingAsset,
            String defectType, String defectAsset, String caseType, String caseAsset) {
        if (!"COMPLETED".equals(runStatus) || !"PROPOSED".equals(recommendationStatus)
                || !java.util.Set.of("OPEN", "UNDER_REVIEW", "ACTION_REQUIRED", "MONITORING").contains(caseStatus)
                || !Objects.equals(findingDefect,defectId)
                || !Objects.equals(recommendationDefect,defectId)
                || !Objects.equals(caseDefect,defectId)
                || !Objects.equals(defectFinding,findingId)
                || !"EQUIPMENT".equals(runType)
                || !Objects.equals(runType,findingType)
                || !Objects.equals(runType,defectType)
                || !Objects.equals(runType,caseType)
                || !Objects.equals(runId,findingAsset)
                || !Objects.equals(runId,defectAsset)
                || !Objects.equals(runId,caseAsset)) {
            throw new IllegalStateException("Integrity finding is not eligible for governed maintenance.");
        }
    }
    public static String required(String text,int limit) {
        if(text==null || text.isBlank() || text.length()>limit || !text.equals(text.strip()))
            throw new IllegalArgumentException("A bounded nonblank Integrity declaration is required.");
        return text;
    }
}
