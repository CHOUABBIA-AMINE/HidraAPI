/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskExternalEvidenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.integration
 *
 * @Description : Resolves external evidence references without importing foreign domain models.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.integration;

/**
 * Resolves external evidence references without importing foreign domain models.
 */
public interface RiskExternalEvidenceResolver {

    boolean evidenceExists(String evidenceModule, String evidenceType, String evidenceId);
}
