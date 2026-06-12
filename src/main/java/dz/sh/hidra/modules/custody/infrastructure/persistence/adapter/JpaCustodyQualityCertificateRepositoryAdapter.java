/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaCustodyQualityCertificateRepositoryAdapter
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Adapter
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for CustodyQualityCertificate.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.custody.application.port.out.CustodyQualityCertificateRepositoryPort;
import dz.sh.hidra.modules.custody.domain.model.CustodyQualityCertificate;
import dz.sh.hidra.modules.custody.infrastructure.persistence.mapper.CustodyPersistenceMapper;
import dz.sh.hidra.modules.custody.infrastructure.persistence.repository.CustodyQualityCertificateJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for CustodyQualityCertificate.
 */
@Component
public class JpaCustodyQualityCertificateRepositoryAdapter implements CustodyQualityCertificateRepositoryPort {

    private final CustodyQualityCertificateJpaRepository repository;

    public JpaCustodyQualityCertificateRepositoryAdapter(CustodyQualityCertificateJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "CustodyQualityCertificateJpaRepository must not be null.");
    }

    @Override
    public CustodyQualityCertificate save(CustodyQualityCertificate model) {
        return CustodyPersistenceMapper.toDomain(repository.save(CustodyPersistenceMapper.toEntity(model)));
    }

    @Override
    public Optional<CustodyQualityCertificate> findById(String id) {
        return repository.findById(id).map(CustodyPersistenceMapper::toDomain);
    }
}
