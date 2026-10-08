/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentGovernanceService
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

import dz.sh.hidra.modules.risk.application.command.*;
import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;
import dz.sh.hidra.modules.risk.application.mapper.RiskApplicationMapper;
import dz.sh.hidra.modules.risk.application.port.in.RiskAssessmentGovernanceUseCase;
import dz.sh.hidra.modules.risk.application.port.out.RiskAssessmentRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentGovernanceService implements RiskAssessmentGovernanceUseCase {
    private final RiskAssessmentRepositoryPort assessments;
    public RiskAssessmentGovernanceService(RiskAssessmentRepositoryPort assessments) { this.assessments=assessments; }
    public RiskAssessmentSummaryDto score(ScoreRiskAssessmentCommand command) { return RiskApplicationMapper.toSummary(assessments.score(command)); }
    public RiskAssessmentSummaryDto submit(String id) { return RiskApplicationMapper.toSummary(assessments.submit(id)); }
    public RiskAssessmentSummaryDto approve(ApproveRiskAssessmentCommand command) { return RiskApplicationMapper.toSummary(assessments.approve(command)); }
}
