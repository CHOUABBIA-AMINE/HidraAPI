/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentVersionRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentVersion.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentVersionRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentVersionJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import dz.sh.hidra.modules.workflow.application.contract.documents.DocumentsApprovalReferenceContract;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentVersion.
 */
@Component
public class JpaDocumentVersionRepositoryAdapter implements DocumentVersionRepositoryPort {

    private final DocumentVersionJpaRepository repository;

    private final DocumentsActorContract actors;
    private final DocumentsApprovalReferenceContract workflow;
    public JpaDocumentVersionRepositoryAdapter(DocumentVersionJpaRepository repository,DocumentsActorContract actors,
            DocumentsApprovalReferenceContract workflow) {
        this.repository = Objects.requireNonNull(repository, "DocumentVersionJpaRepository must not be null.");
        this.actors=Objects.requireNonNull(actors);this.workflow=Objects.requireNonNull(workflow);
    }

    @Override
    @Transactional
    public DocumentVersion save(DocumentVersion model) {
        Objects.requireNonNull(model);
        actors.eligibleActor(model.uploadedByActorId(),Instant.now()).filter(a->model.uploadedByActorId().equals(a.id()))
            .orElseThrow(()->new IllegalArgumentException("Eligible Identity uploader required."));
        if(model.approvedByWorkflowInstanceId()!=null && !workflow.exists(model.approvedByWorkflowInstanceId()))
            throw new IllegalArgumentException("Existing Workflow approval reference required.");
        if(model.supersededByVersionId()!=null && !repository.existsById(model.supersededByVersionId()))
            throw new IllegalArgumentException("Existing superseding version required.");
        return DocumentsPersistenceMapper.toDomain(repository.saveAndFlush(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentVersion> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
