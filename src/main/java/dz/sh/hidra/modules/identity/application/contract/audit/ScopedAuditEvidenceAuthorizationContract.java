/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceAuthorizationContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.contract.audit
 *
 * @Description : Defines exact T28 governed scoped Audit evidence.
 *
 */
package dz.sh.hidra.modules.identity.application.contract.audit;

/** Only signed native LOCAL caller and both live Audit scope grants are exported. */
public interface ScopedAuditEvidenceAuthorizationContract {
    String nativeActor();
    boolean permitted(String actor,String companyId,String facilityId,boolean export);
}
