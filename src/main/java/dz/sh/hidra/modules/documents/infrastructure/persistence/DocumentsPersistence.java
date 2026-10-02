/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence
 *
 * @Description : Documents database table constants.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence;

/**
 * Documents database table constants.
 */
public final class DocumentsPersistence {

    public static final String DOCUMENT_TABLE = "hidra_documents_document";
    public static final String DOCUMENT_VERSION_TABLE = "hidra_documents_document_version";
    public static final String DOCUMENT_STORAGE_OBJECT_TABLE = "hidra_documents_storage_object";
    public static final String DOCUMENT_TARGET_LINK_TABLE = "hidra_documents_target_link";
    public static final String DOCUMENT_ACCESS_GRANT_TABLE = "hidra_documents_access_grant";
    public static final String DOCUMENT_REVIEW_REFERENCE_TABLE = "hidra_documents_review_reference";
    public static final String DOCUMENT_RETENTION_RECORD_TABLE = "hidra_documents_retention_record";
    public static final String DOCUMENT_EXTRACTION_RECORD_TABLE = "hidra_documents_extraction_record";
    public static final String DOCUMENT_EXTERNAL_REFERENCE_TABLE = "hidra_documents_external_reference";
    public static final String DOCUMENT_CATALOG_ENTRY_TABLE = "hidra_documents_catalog_entry";
    public static final String DOCUMENT_CATALOG_TRANSLATION_TABLE = "hidra_documents_catalog_translation";

    private DocumentsPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
