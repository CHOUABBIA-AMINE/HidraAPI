/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsModuleConfiguration
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.configuration
 *
 * @Description : Documents infrastructure configuration.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.configuration;

/**
 * Documents infrastructure configuration.
 */
public record DocumentsModuleConfiguration(
        boolean checksumRequired,
        boolean accessGrantEnabled,
        boolean extractionEnabled,
        boolean signedUrlPersistenceBlocked
) {

    public static DocumentsModuleConfiguration defaults() {
        return new DocumentsModuleConfiguration(true, true, true, true);
    }
}
