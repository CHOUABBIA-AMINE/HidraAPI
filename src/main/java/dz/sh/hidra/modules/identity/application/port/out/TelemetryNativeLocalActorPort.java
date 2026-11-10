/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryNativeLocalActorPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-10
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.port.out
 *
 * @Description : Verifies a current LOCAL native bearer and its retained operation binding.
 *
 */
package dz.sh.hidra.modules.identity.application.port.out;


public interface TelemetryNativeLocalActorPort {
    String currentLocalActor();
}
