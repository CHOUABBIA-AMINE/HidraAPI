/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditTargetSnapshotValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.domain.value
 *
 * @Description : Target evidence snapshot.
 *
 */
package dz.sh.hidra.modules.audit.domain.value;

/**
 * Target evidence snapshot.
 *
 * @param targetModule target module
 * @param targetType target type
 * @param targetId target identifier
 * @param targetLabelSnapshot target label snapshot
 */
public record AuditTargetSnapshotValue(
        String targetModule,
        String targetType,
        String targetId,
        String targetLabelSnapshot
) {
}
