/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopRiskExternalEvidenceResolver
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.integration
 *
 * @Description : No-op risk external evidence resolver.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.integration;

/**
 * No-op risk external evidence resolver.
 */
public class NoopRiskExternalEvidenceResolver implements RiskExternalEvidenceResolver {

    @Override
    public boolean evidenceExists(String evidenceModule, String evidenceType, String evidenceId) {
        return true;
    }
}
