/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Name        : AlarmResourceAuthorizationPort
 * @UpdatedOn   : 2026-11-08
 * @Type        : Interface
 * @Layer       : Interface
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 * @Description : Alarm query and command resource-scope port.
 */
package dz.sh.hidra.modules.alarm.application.port.out;

import java.util.List;
public interface AlarmResourceAuthorizationPort {
    record AssetRef(String typeCode,String assetId) {
        public AssetRef {
            if(typeCode==null||typeCode.isBlank()||assetId==null||assetId.isBlank())
                throw new IllegalArgumentException("Exact Alarm asset required.");
        }
    }
    List<AssetRef> permittedAssets(String permission);
    void requireAsset(String permission,String typeCode,String assetId);
    void requireAlarm(String permission,String alarmId);
}
