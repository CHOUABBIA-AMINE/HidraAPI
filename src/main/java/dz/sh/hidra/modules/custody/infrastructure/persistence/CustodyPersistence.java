/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPersistence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.persistence
 *
 * @Description : Custody database table constants.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.persistence;

/**
 * Custody database table constants.
 */
public final class CustodyPersistence {

    public static final String CUSTODY_TRANSFER_POINT_TABLE = "hidra_custody_transfer_point";
    public static final String CUSTODY_AGREEMENT_TABLE = "hidra_custody_agreement";
    public static final String CUSTODY_AGREEMENT_PARTY_TABLE = "hidra_custody_agreement_party";
    public static final String CUSTODY_MEASUREMENT_PERIOD_TABLE = "hidra_custody_measurement_period";
    public static final String CUSTODY_BATCH_TABLE = "hidra_custody_batch";
    public static final String CUSTODY_METERING_SYSTEM_TABLE = "hidra_custody_metering_system";
    public static final String CUSTODY_METER_RUN_SNAPSHOT_TABLE = "hidra_custody_meter_run_snapshot";
    public static final String CUSTODY_MEASUREMENT_SNAPSHOT_TABLE = "hidra_custody_measurement_snapshot";
    public static final String CUSTODY_QUALITY_SAMPLE_TABLE = "hidra_custody_quality_sample";
    public static final String CUSTODY_QUALITY_CERTIFICATE_TABLE = "hidra_custody_quality_certificate";
    public static final String CUSTODY_QUANTITY_CALCULATION_TABLE = "hidra_custody_quantity_calculation";
    public static final String CUSTODY_CORRECTION_FACTOR_TABLE = "hidra_custody_correction_factor";
    public static final String CUSTODY_TRANSFER_TICKET_TABLE = "hidra_custody_transfer_ticket";
    public static final String CUSTODY_TICKET_LINE_TABLE = "hidra_custody_ticket_line";
    public static final String CUSTODY_RECONCILIATION_TABLE = "hidra_custody_reconciliation";
    public static final String CUSTODY_DISCREPANCY_TABLE = "hidra_custody_discrepancy";
    public static final String CUSTODY_APPROVAL_REFERENCE_TABLE = "hidra_custody_approval_reference";
    public static final String CUSTODY_DOCUMENT_REFERENCE_TABLE = "hidra_custody_document_reference";
    public static final String CUSTODY_CATALOG_ENTRY_TABLE = "hidra_custody_catalog_entry";
    public static final String CUSTODY_CATALOG_TRANSLATION_TABLE = "hidra_custody_catalog_translation";

    private CustodyPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
