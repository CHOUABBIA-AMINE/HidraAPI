/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : SyntheticExchangeOwnerAccessAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.integration
 * @Description : Sole Integration adapter for authenticated Identity, Topology and Documents exports.
 */
package dz.sh.hidra.modules.integration.infrastructure.integration;
import dz.sh.hidra.modules.integration.application.port.out.SyntheticExchangeOwnerPort;
import dz.sh.hidra.modules.integration.domain.model.SyntheticExchange.Scope;
import dz.sh.hidra.modules.identity.application.contract.integration.IntegrationSyntheticExchangeAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.integration.IntegrationSyntheticFacilityOwnershipContract;
import dz.sh.hidra.modules.documents.application.contract.integration.IntegrationSyntheticDraftReceiptContract;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation=Propagation.MANDATORY)
public class SyntheticExchangeOwnerAccessAdapter implements SyntheticExchangeOwnerPort {
    private final IntegrationSyntheticExchangeAuthorizationContract authority;
    private final IntegrationSyntheticFacilityOwnershipContract topology;
    private final IntegrationSyntheticDraftReceiptContract documents;
    public SyntheticExchangeOwnerAccessAdapter(
            IntegrationSyntheticExchangeAuthorizationContract authority,
            IntegrationSyntheticFacilityOwnershipContract topology,
            IntegrationSyntheticDraftReceiptContract documents) {
        this.authority=Objects.requireNonNull(authority);
        this.topology=Objects.requireNonNull(topology);
        this.documents=Objects.requireNonNull(documents);
    }
    @Override
    public String require(Scope scope,Operation action) {
        String actor=authority.require("integration:synthetic-exchange:read",
                scope.companyId(),scope.facilityId());
        if (action!=Operation.READ) authority.require("integration:synthetic-exchange:"
                +action.name().toLowerCase(java.util.Locale.ROOT),scope.companyId(),scope.facilityId());
        var current=topology.current(scope.facilityId())
                .orElseThrow(SyntheticExchangeOwnerAccessAdapter::hidden);
        if (!scope.companyId().equals(current.companyId())) throw hidden();
        return actor;
    }
    @Override
    public void requireDraft(Scope scope) {
        documents.requireAuthority(scope.companyId(),scope.facilityId());
    }
    @Override
    public Draft createDraft(Scope scope,String exchangeId,String title,String documentTypeId,
            String classificationId) {
        try {
            var draft=documents.create(scope.companyId(),scope.facilityId(),exchangeId,
                    title,documentTypeId,classificationId);
            return new Draft(draft.documentId(),draft.replayed());
        } catch (TransientDataAccessException retryable) {
            throw new TransientReceiverFailure(retryable);
        }
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected exchange unavailable.");
    }
}
