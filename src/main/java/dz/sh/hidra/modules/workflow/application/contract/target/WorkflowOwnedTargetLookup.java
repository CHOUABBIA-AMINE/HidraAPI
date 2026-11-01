/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowOwnedTargetLookup
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.contract.target
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.contract.target;

import java.util.Optional;
import java.util.Set;
public interface WorkflowOwnedTargetLookup {
    record Target(String id, String code, String label) {}
    String module();
    Set<String> targetTypeCodes();
    Optional<Target> eligibleTarget(String typeCode, String targetId);

    /** Untrusted owner locator; owner performs native authorization before revealing the target. */
    default Optional<Target> eligibleTarget(String typeCode, String targetId, String locator) {
        return eligibleTarget(typeCode, targetId);
    }

    /** Protected manufacturer targets must explicitly opt into each live owner-authorized disclosure.
     * Ordinary Workflow task membership does not grant this access.
     * Default denial is deliberate; other owner lookups are not invoked for this policy. */
    enum ProtectedRead { REVISION, HISTORY }
    default boolean protectedReadAllowed(String typeCode, String targetDigest, ProtectedRead operation) {
        return false;
    }

    /** Locator is only an address hint, never a permission or target proof. */
    default boolean protectedReadAllowed(String typeCode, String digest, String locator, ProtectedRead operation) {
        return protectedReadAllowed(typeCode, digest, operation);
    }
}
