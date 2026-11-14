/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEvidenceLinkPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.application.port.out
 *
 * @Description : Appends and checks Alarm-owned typed source evidence.
 *
 */
package dz.sh.hidra.modules.alarm.application.port.out;

public interface AlarmEvidenceLinkPort {
    void linkEvaluation(String alarmId, String evaluationId);
    boolean hasEvaluation(String alarmId, String evaluationId);
}
