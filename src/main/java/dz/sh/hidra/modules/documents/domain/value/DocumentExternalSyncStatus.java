/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExternalSyncStatus
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Defines DocumentExternalSyncStatus values.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Defines DocumentExternalSyncStatus values.
 */
public enum DocumentExternalSyncStatus {
    NOT_SYNCED, SYNCED, FAILED, CONFLICT, DEPRECATED
}
