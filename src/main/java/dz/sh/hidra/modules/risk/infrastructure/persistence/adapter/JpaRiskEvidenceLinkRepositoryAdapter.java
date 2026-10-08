/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaRiskEvidenceLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for RiskEvidenceLink.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLinkRepositoryPort;
import dz.sh.hidra.modules.risk.domain.model.RiskEvidenceLink;
import dz.sh.hidra.modules.risk.infrastructure.persistence.mapper.RiskPersistenceMapper;
import dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskEvidenceLinkJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for RiskEvidenceLink.
 */
@Component
public class JpaRiskEvidenceLinkRepositoryAdapter implements RiskEvidenceLinkRepositoryPort {

    private final RiskEvidenceLinkJpaRepository repository;
    private final dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAssessmentJpaRepository assessments;

    private final dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLookupPort evidenceLookup;

    public JpaRiskEvidenceLinkRepositoryAdapter(RiskEvidenceLinkJpaRepository repository) {
        this(repository, null, link -> { throw new dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException(
                "Evidence owner validation is not configured."); });
    }

    @org.springframework.beans.factory.annotation.Autowired
    public JpaRiskEvidenceLinkRepositoryAdapter(RiskEvidenceLinkJpaRepository repository,
            dz.sh.hidra.modules.risk.infrastructure.persistence.repository.RiskAssessmentJpaRepository assessments,
            dz.sh.hidra.modules.risk.application.port.out.RiskEvidenceLookupPort evidenceLookup) {
        this.assessments = assessments;
        this.evidenceLookup = Objects.requireNonNull(evidenceLookup);
        this.repository = Objects.requireNonNull(repository, "RiskEvidenceLinkJpaRepository must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public RiskEvidenceLink save(RiskEvidenceLink model) {
        if (assessments == null) throw new IllegalStateException("Governed assessment persistence is not configured.");
        var parent = assessments.findLocked(model.riskAssessmentId()).orElseThrow();
        if (parent.status()==dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus.APPROVED
                || parent.status()==dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus.ACTIVE)
            throw new dz.sh.hidra.modules.risk.domain.exception.InvalidRiskValueException("Approved evidence associations are immutable.");
        model = evidenceLookup.validate(model);
        return RiskPersistenceMapper.toDomain(repository.save(RiskPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<RiskEvidenceLink> findById(String id) {
        return repository.findById(id).map(RiskPersistenceMapper::toDomain);
    }
}
