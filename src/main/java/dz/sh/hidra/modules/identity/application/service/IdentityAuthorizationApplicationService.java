/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAuthorizationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service for identity authorization evaluation.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import org.springframework.stereotype.Service;

import dz.sh.hidra.modules.identity.application.dto.PermissionDecisionDto;
import dz.sh.hidra.modules.identity.application.port.in.EvaluatePermissionUseCase;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionRepositoryPort;
import dz.sh.hidra.modules.identity.application.query.EvaluatePermissionQuery;
import dz.sh.hidra.modules.identity.domain.model.AuthorizationDecision;
import dz.sh.hidra.modules.identity.domain.policy.AuthorizationEvaluationRequest;
import dz.sh.hidra.modules.identity.domain.service.AuthorizationPolicyEvaluator;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationDecisionValue;

import java.util.Objects;
import java.time.Instant;
import org.springframework.transaction.annotation.Transactional;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationEvidencePort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationAssertionPort;
import dz.sh.hidra.modules.identity.application.port.out.AuthorizationDecisionSettingsPort;

/**
 * Application service for identity authorization evaluation.
 */
@Service
public class IdentityAuthorizationApplicationService implements EvaluatePermissionUseCase {

    private final AuthorizationDecisionRepositoryPort authorizationDecisionRepositoryPort;
    private final AuthorizationPolicyEvaluator authorizationPolicyEvaluator;

    private final AuthorizationEvidencePort evidencePort;
    private final AuthorizationAssertionPort assertionPort;
    private final AuthorizationDecisionSettingsPort settingsPort;

    public IdentityAuthorizationApplicationService(
            AuthorizationDecisionRepositoryPort authorizationDecisionRepositoryPort,
            AuthorizationPolicyEvaluator authorizationPolicyEvaluator,
            AuthorizationEvidencePort evidencePort,
            AuthorizationAssertionPort assertionPort,
            AuthorizationDecisionSettingsPort settingsPort
    ) {
        this.evidencePort=Objects.requireNonNull(evidencePort);
        this.assertionPort=Objects.requireNonNull(assertionPort);
        this.settingsPort=Objects.requireNonNull(settingsPort);
        this.authorizationDecisionRepositoryPort = Objects.requireNonNull(
                authorizationDecisionRepositoryPort,
                "Authorization decision repository port must not be null."
        );
        this.authorizationPolicyEvaluator = Objects.requireNonNull(
                authorizationPolicyEvaluator,
                "Authorization policy evaluator must not be null."
        );
    }

    @Override
    @Transactional
    public PermissionDecisionDto evaluate(EvaluatePermissionQuery query) {
        Objects.requireNonNull(query, "Evaluate permission query must not be null.");

        AuthorizationEvaluationRequest request = new AuthorizationEvaluationRequest(
                query.userId(),
                query.permissionCode(),
                query.resourceType(),
                query.resourceReferenceId(),
                query.scope()
        );

        Instant at=Instant.now();
        AuthorizationDecision decision = authorizationPolicyEvaluator.evaluate(request,
                evidencePort.load(request,at,assertionPort.currentFor(query.userId())),at);
        AuthorizationDecision savedDecision = settingsPort.persistenceEnabled()
                ? authorizationDecisionRepositoryPort.save(decision) : decision;

        return new PermissionDecisionDto(
                savedDecision.decision() == AuthorizationDecisionValue.PERMIT,
                savedDecision.decision(),
                savedDecision.reasonCode(),
                savedDecision.reasonMessage(),
                savedDecision.evaluatedAt()
        );
    }
}
