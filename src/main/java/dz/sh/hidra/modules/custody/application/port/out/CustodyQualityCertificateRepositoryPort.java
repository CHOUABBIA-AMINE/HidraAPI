/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyQualityCertificateRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.out
 *
 * @Description : Repository port for CustodyQualityCertificate.
 *
 */
package dz.sh.hidra.modules.custody.application.port.out;

import dz.sh.hidra.modules.custody.domain.model.CustodyQualityCertificate;

import java.util.Optional;

/**
 * Repository port for CustodyQualityCertificate.
 */
public interface CustodyQualityCertificateRepositoryPort {

    CustodyQualityCertificate save(CustodyQualityCertificate model);

    Optional<CustodyQualityCertificate> findById(String id);
}
