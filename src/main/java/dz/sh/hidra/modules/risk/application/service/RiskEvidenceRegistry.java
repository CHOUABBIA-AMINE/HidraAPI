/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskEvidenceRegistry
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.service
 *
 * @Description : Enforces governed Risk owner boundaries.
 *
 */
package dz.sh.hidra.modules.risk.application.service;

import dz.sh.hidra.modules.risk.application.contract.evidence.RiskOwnedEvidenceLookup;
import dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLookupPort;
import dz.sh.hidra.modules.risk.domain.model.RiskEvidenceLink;
import dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class RiskEvidenceRegistry implements RiskEvidenceLookupPort {
    private final List<RiskOwnedEvidenceLookup> providers;
    public RiskEvidenceRegistry(List<RiskOwnedEvidenceLookup> providers) {
        this.providers = List.copyOf(providers);
    }
    @Override
    public RiskEvidenceLink validate(RiskEvidenceLink link) {
        Objects.requireNonNull(link, "Evidence must not be null.");
        var matches = providers.stream().filter(p -> link.evidenceModule().equals(p.module())
                && p.evidenceTypes().contains(link.evidenceType())).toList();
        if (matches.size() != 1) throw new InvalidRiskValueException("Evidence owner/type is unsupported or ambiguous.");
        var source = matches.get(0).resolve(link.evidenceType(), link.evidenceId())
                .filter(e -> link.evidenceId().equals(e.id()))
                .orElseThrow(() -> new InvalidRiskValueException("Evidence is unavailable from its registered owner."));
        return new RiskEvidenceLink(link.id(), link.riskAssessmentId(), link.evidenceModule(),
                link.evidenceType(), link.evidenceId(),
                source.code() == null ? link.evidenceCodeSnapshot() : source.code(),
                source.label() == null ? link.evidenceLabelSnapshot() : source.label(),
                source.timestamp() == null ? link.evidenceTimestamp() : source.timestamp(),
                source.hash() == null ? link.evidenceHash() : source.hash(), link.evidenceSummary(), link.createdAt());
    }
}
