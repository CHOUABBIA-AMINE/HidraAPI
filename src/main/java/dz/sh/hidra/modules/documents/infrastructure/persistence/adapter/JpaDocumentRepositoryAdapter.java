/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaDocumentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Document.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.documents.application.port.out.DocumentRepositoryPort;
import dz.sh.hidra.modules.documents.domain.model.Document;
import dz.sh.hidra.modules.documents.infrastructure.persistence.mapper.DocumentsPersistenceMapper;
import dz.sh.hidra.modules.documents.infrastructure.persistence.repository.DocumentJpaRepository;
import org.springframework.stereotype.Component;

import dz.sh.hidra.modules.documents.application.port.out.DocumentsCatalogEligibilityPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentTargetLookupPort;
import dz.sh.hidra.modules.documents.application.port.out.DocumentVersionRepositoryPort;
import dz.sh.hidra.modules.identity.application.contract.documents.DocumentsActorContract;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Document.
 */
@Component
public class JpaDocumentRepositoryAdapter implements DocumentRepositoryPort {

    private final DocumentJpaRepository repository;

    private final DocumentsCatalogEligibilityPort catalogs;
    private final DocumentTargetLookupPort targets;
    private final DocumentsActorContract actors;
    private final DocumentVersionRepositoryPort versions;
    public JpaDocumentRepositoryAdapter(DocumentJpaRepository repository, DocumentsCatalogEligibilityPort catalogs,
            DocumentTargetLookupPort targets, DocumentsActorContract actors, DocumentVersionRepositoryPort versions) {
        this.repository = Objects.requireNonNull(repository, "DocumentJpaRepository must not be null.");
        this.catalogs=Objects.requireNonNull(catalogs);this.targets=Objects.requireNonNull(targets);
        this.actors=Objects.requireNonNull(actors);this.versions=Objects.requireNonNull(versions);
    }

    @Override
    @Transactional
    public Document save(Document model) {
        Objects.requireNonNull(model);
        catalogs.requireActive(model.documentTypeId(),"DOCUMENT_TYPE");
        catalogs.requireActive(model.classificationId(),"DOCUMENT_CLASSIFICATION");
        if(model.documentCategoryId()!=null)catalogs.requireActive(model.documentCategoryId(),"DOCUMENT_CATEGORY");
        actors.eligibleActor(model.createdByActorId(),Instant.now()).filter(a->model.createdByActorId().equals(a.id()))
            .orElseThrow(()->new IllegalArgumentException("Eligible document creator required."));
        if(model.ownerModule()!=null)targets.requireTarget(model.ownerModule(),model.ownerTargetTypeCode(),model.ownerTargetId());
        if(model.currentVersionId()!=null)versions.findById(model.currentVersionId()).filter(v->model.id().equals(v.documentId()))
            .orElseThrow(()->new IllegalArgumentException("Current version must belong to this document."));
        return DocumentsPersistenceMapper.toDomain(repository.saveAndFlush(DocumentsPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<Document> findById(String id) {
        return repository.findById(id).map(DocumentsPersistenceMapper::toDomain);
    }
}
