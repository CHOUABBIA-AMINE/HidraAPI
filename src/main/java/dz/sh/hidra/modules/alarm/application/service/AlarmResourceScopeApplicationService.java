/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name : AlarmResourceScopeApplicationService
 * @UpdatedOn : 2026-11-08
 * @Layer : Application
 * @Module : alarm
 * @Package : dz.sh.hidra.modules.alarm.application.service
 * @Description : Orchestrates Alarm scope checks through the owner-backed outbound port.
 */
package dz.sh.hidra.modules.alarm.application.service;

import dz.sh.hidra.modules.alarm.application.port.in.AlarmResourceScopeUseCase;
import dz.sh.hidra.modules.alarm.application.port.out.AlarmResourceAuthorizationPort;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** Scope checks must run in the caller's original protected transaction. */
@Service
public class AlarmResourceScopeApplicationService implements AlarmResourceScopeUseCase {
    private final AlarmResourceAuthorizationPort scope;

    public AlarmResourceScopeApplicationService(AlarmResourceAuthorizationPort scope) {
        this.scope = Objects.requireNonNull(scope);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireAsset(String permissionCode, String assetType, String assetId) {
        scope.requireAsset(permissionCode, assetType, assetId);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void requireAlarm(String permissionCode, String alarmId) {
        scope.requireAlarm(permissionCode, alarmId);
    }
}
