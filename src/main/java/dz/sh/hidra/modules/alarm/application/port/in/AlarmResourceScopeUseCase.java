/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name : AlarmResourceScopeUseCase
 * @UpdatedOn : 2026-11-08
 * @Layer : Application
 * @Module : alarm
 * @Package : dz.sh.hidra.modules.alarm.application.port.in
 * @Description : Operator-facing admission for a single current Alarm asset.
 */
package dz.sh.hidra.modules.alarm.application.port.in;

/** API adapters invoke only this inbound contract, never a persistence/security outbound port. */
public interface AlarmResourceScopeUseCase {
    void requireAsset(String permissionCode, String assetType, String assetId);
    void requireAlarm(String permissionCode, String alarmId);
}
