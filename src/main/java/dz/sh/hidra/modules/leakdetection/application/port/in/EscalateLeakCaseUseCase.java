/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EscalateLeakCaseUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.in
 *
 * @Description : Use case for escalating leak cases.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.in;

import dz.sh.hidra.modules.leakdetection.application.command.EscalateLeakCaseCommand;

/**
 * Use case for escalating leak cases by neutral reference.
 */
public interface EscalateLeakCaseUseCase {

    String escalateLeakCase(EscalateLeakCaseCommand command);
}
