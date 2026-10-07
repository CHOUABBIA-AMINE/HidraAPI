/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentTargetLinkRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for DocumentTargetLink.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLinkRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.DocumentTargetLink;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentTargetLinkJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.documents.application.port.out.DocumentsCatalogEligibilityPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLookupPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentVersionRepositoryPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for DocumentTargetLink.
 */
@Component
public class JpaDocumentTargetLinkRepositoryAdapter implements DocumentTargetLinkRepositoryPort {

    private final DocumentTargetLinkJpaRepository repository;

    private final DocumentsCatalogEligibilityPort catalogs;
    private final DocumentTargetLookupPort targets;
    private final DocumentVersionRepositoryPort versions;
    private final DocumentRepositoryPort documents;
    private final DocumentsActorContract actors;
    public JpaDocumentTargetLinkRepositoryAdapter(DocumentTargetLinkJpaRepository repository, DocumentsCatalogEligibilityPort catalogs,
            DocumentTargetLookupPort targets,DocumentVersionRepositoryPort versions,DocumentRepositoryPort documents,DocumentsActorContract actors) {
        this.repository = Objects.requireNonNull(repository, "DocumentTargetLinkJpaRepository must not be null.");
        this.catalogs=Objects.requireNonNull(catalogs);this.targets=Objects.requireNonNull(targets);
        this.versions=Objects.requireNonNull(versions);this.documents=Objects.requireNonNull(documents);this.actors=Objects.requireNonNull(actors);
    }

    @Override
    @Transactional
    public DocumentTargetLink save(DocumentTargetLink model) {
        Objects.requireNonNull(model);
        catalogs.requireActive(model.linkRoleId(),"DOCUMENT_LINK_ROLE");
        if(documents.findById(model.documentId()).isEmpty())throw new IllegalArgumentException("Existing document required.");
        if(model.documentVersionId()!=null)versions.findById(model.documentVersionId()).filter(v->model.documentId().equals(v.documentId()))
            .orElseThrow(()->new IllegalArgumentException("Linked version must belong to this document."));
        targets.requireTarget(model.targetModule(),model.targetTypeCode(),model.targetId());
        actors.eligibleActor(model.linkedByActorId(),Instant.now()).filter(a->model.linkedByActorId().equals(a.id()))
            .orElseThrow(()->new IllegalArgumentException("Eligible linking actor required."));
        return DocumentsPersistenceMapper.toDomain(repository.saveAndFlush(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<DocumentTargetLink> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
