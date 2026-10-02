/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PayloadStorageMode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.domain.value
 *
 * @Description : Defines PayloadStorageMode values.
 *
 */
package dz.sh.hidra.modules.integration.domain.value;

/**
 * Defines PayloadStorageMode values.
 */
public enum PayloadStorageMode {
    INLINE_SANITIZED, OBJECT_STORAGE_REFERENCE, HASH_ONLY
}
