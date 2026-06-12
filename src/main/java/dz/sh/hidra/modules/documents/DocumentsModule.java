/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsModule
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents
 *
 * @Description : Defines documents module constants.
 *
 */
package dz.sh.hidra.modules.documents;

/**
 * Documents module constants.
 */
public final class DocumentsModule {

    public static final String MODULE_NAME = "documents";
    public static final String TABLE_PREFIX = "hidra_documents_";

    private DocumentsModule() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
