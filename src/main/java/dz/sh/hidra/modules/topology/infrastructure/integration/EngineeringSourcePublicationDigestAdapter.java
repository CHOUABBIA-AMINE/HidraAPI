/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourcePublicationDigestAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Hashes the complete canonical publication using the Topology publication codec.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.application.port.out.EngineeringSourcePublicationDigestPort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.Snapshot;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCodec;
import org.springframework.stereotype.Component;

@Component
public final class EngineeringSourcePublicationDigestAdapter implements EngineeringSourcePublicationDigestPort {
    private final EngineeringRevisionPublicationCodec codec = new EngineeringRevisionPublicationCodec();

    @Override
    public String sha256(Snapshot snapshot) {
        return EngineeringRevisionPublication.hash(codec.encode(snapshot));
    }
}
