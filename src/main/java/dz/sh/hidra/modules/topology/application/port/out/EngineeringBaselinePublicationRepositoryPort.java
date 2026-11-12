/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselinePublicationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Implements the exact engineering baseline publication contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselinePublicationUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort.Original;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort.Read;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import java.time.Instant;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselinePublication.*;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EngineeringBaselinePublicationRepositoryPort {
    void lock();
    boolean scopeAvailable(String company, String pipeline);
    Submission draft(Command command, Original origin);
    Submission submission(String company, String pipeline, UUID id);
    Receipt receipt(String company, String pipeline, UUID id);
    long head(String company, String pipeline);
    Receipt current(String company, String pipeline);
    Receipt effective(String company, String pipeline, Instant asOf);
    List<Receipt> history(String company, String pipeline, Instant asOf, long highWater, long beforeEpoch, int take);
    List<Read> historicalSourceReads(EngineeringBaselineManifest manifest);
    boolean published(String company, String pipeline, UUID submission);
    Set<String> mutationSql();
    void insert(Submission submission);
    void insert(Receipt receipt);
    boolean guard(Original origin, List<ProtectedRelationFence> fences);
    List<ProtectedRelationFence> fences();
}
