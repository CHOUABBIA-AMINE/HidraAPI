/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentAccessLevel
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Enum
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.value
 *
 * @Description : Defines DocumentAccessLevel values.
 *
 */
package dz.sh.hidra.modules.documents.domain.value;

/**
 * Defines DocumentAccessLevel values.
 */
public enum DocumentAccessLevel {
    READ, DOWNLOAD, UPDATE_METADATA, UPLOAD_VERSION, APPROVE, ARCHIVE, ADMIN
}
