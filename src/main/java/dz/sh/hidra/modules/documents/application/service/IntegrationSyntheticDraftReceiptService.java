/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrationSyntheticDraftReceiptService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.service
 * @Description : T24-owned idempotent receipt and current catalog validation for synthetic Integration drafts.
 */
package dz.sh.hidra.modules.documents.application.service;
import dz.sh.hidra.modules.documents.application.contract.integration.IntegrationSyntheticDraftReceiptContract;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase;
import dz.sh.hidra.modules.documents.application.port.out.ControlledDocumentAuthorizationPort;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.MANDATORY)
public class IntegrationSyntheticDraftReceiptService
        implements IntegrationSyntheticDraftReceiptContract {
    private final ControlledDocumentsUseCase documents;
    private final ControlledDocumentAuthorizationPort authority;
    public IntegrationSyntheticDraftReceiptService(ControlledDocumentsUseCase documents,
            ControlledDocumentAuthorizationPort authority) {
        this.documents=Objects.requireNonNull(documents);
        this.authority=Objects.requireNonNull(authority);
    }
    @Override
    public void requireAuthority(String companyId,String facilityId) {
        authority.require("documents:controlled:read",companyId,facilityId);
        authority.require("documents:controlled:create",companyId,facilityId);
        var facility=authority.requireActiveFacility(facilityId);
        if (!companyId.equals(facility.companyId())) throw hidden();
    }
    @Override
    public Draft create(String companyId,String facilityId,String exchangeId,
            String titleFr,String documentTypeId,String classificationId) {
        requireAuthority(companyId,facilityId);
        if (exchangeId==null || !exchangeId.matches("[a-f0-9\\-]{36}"))
            throw hidden();
        String derived="T26-"+exchangeId;
        var created=documents.create(companyId,facilityId,
                new ControlledDocumentsUseCase.Create(derived,derived,titleFr,documentTypeId,classificationId));
        return new Draft(created.document().id(),created.replayed());
    }
    private static NoSuchElementException hidden() {
        return new NoSuchElementException("Protected exchange receiver unavailable.");
    }
}
