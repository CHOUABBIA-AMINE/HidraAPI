/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SyncRecordOperation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.domain.value
 *
 * @Description : Defines identity SyncRecordOperation values.
 *
 */
package dz.sh.hidra.modules.identity.domain.value;

/**
 * Identity SyncRecordOperation values.
 */
public enum SyncRecordOperation {
    CREATED, UPDATED, DISABLED, LINKED, UNLINKED, SKIPPED, FAILED
}
