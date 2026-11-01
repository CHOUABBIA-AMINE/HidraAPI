/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Separates original-transaction native admission from immutable draft-link storage.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Command;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase.Result;

/**
 * Neither marker grants permission. Implementations must verify the original
 * Identity/Party/Topology/Simulation evidence, backend role and SQL transaction,
 * and must reject untrusted or stale admissions. No adapter is wired in C1.
 */
public interface InstalledCharacteristicLinkRepositoryPort {
    interface Prepared {}
    interface Admission {}
    Prepared prepare(Command command);
    Admission acquire(Prepared prepared, Command command);
    Result perform(Admission admission, Command command);
    void flush();
    void prepareFinal(Admission admission);
    boolean finalGuard(Admission admission);
}
