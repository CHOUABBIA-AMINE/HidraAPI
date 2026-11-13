/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleAuthorizationPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Requires current Identity, Topology and Telemetry owner admission.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import java.time.Instant;

public interface MonitoringRuleAuthorizationPort {
    enum Operation { CREATE, REVISE, ACTIVATE, READ, EVALUATE }
    record Scope(String company,String actor){ }
    Scope require(Operation operation,String assetType,String assetId,String expectedCompany);
    void requirePoint(String pointId,String assetType,String assetId,String unitId,Instant at);
}
