/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence
 *
 * @Description : Party database table constants.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence;

/**
 * Party database table constants.
 */
public final class PartyPersistence {

    public static final String PARTY_TABLE = "hidra_party_party";
    public static final String PARTY_TYPE_TABLE = "hidra_party_type";
    public static final String PARTY_TYPE_TRANSLATION_TABLE = "hidra_party_type_translation";
    public static final String PARTY_ROLE_TABLE = "hidra_party_role";
    public static final String PARTY_ROLE_TRANSLATION_TABLE = "hidra_party_role_translation";
    public static final String PARTY_ROLE_ASSIGNMENT_TABLE = "hidra_party_role_assignment";
    public static final String PARTY_LEGAL_PROFILE_TABLE = "hidra_party_legal_profile";
    public static final String PARTY_REGISTRATION_TABLE = "hidra_party_registration";
    public static final String PARTY_TAX_IDENTIFIER_TABLE = "hidra_party_tax_identifier";
    public static final String PARTY_ADDRESS_TABLE = "hidra_party_address";
    public static final String PARTY_CONTACT_POINT_TABLE = "hidra_party_contact_point";
    public static final String PARTY_CONTACT_PERSON_TABLE = "hidra_party_contact_person";
    public static final String PARTY_BANK_REFERENCE_TABLE = "hidra_party_bank_reference";
    public static final String PARTY_QUALIFICATION_TABLE = "hidra_party_qualification";
    public static final String SUPPLIER_QUALIFICATION_TABLE = "hidra_party_supplier_qualification";
    public static final String VENDOR_QUALIFICATION_TABLE = "hidra_party_vendor_qualification";
    public static final String CONTRACTOR_QUALIFICATION_TABLE = "hidra_party_contractor_qualification";
    public static final String MANUFACTURER_PROFILE_TABLE = "hidra_party_manufacturer_profile";
    public static final String OWNER_PROFILE_TABLE = "hidra_party_owner_profile";
    public static final String OPERATOR_PROFILE_TABLE = "hidra_party_operator_profile";
    public static final String PARTY_CERTIFICATION_TABLE = "hidra_party_certification";
    public static final String PARTY_COMPLIANCE_STATUS_TABLE = "hidra_party_compliance_status";
    public static final String PARTY_RISK_SNAPSHOT_TABLE = "hidra_party_risk_snapshot";
    public static final String PARTY_RELATIONSHIP_TABLE = "hidra_party_relationship";
    public static final String PARTY_OWNERSHIP_LINK_TABLE = "hidra_party_ownership_link";
    public static final String PARTY_DOCUMENT_REFERENCE_TABLE = "hidra_party_document_reference";
    public static final String PARTY_EXTERNAL_REFERENCE_TABLE = "hidra_party_external_reference";
    public static final String PARTY_STATUS_HISTORY_TABLE = "hidra_party_status_history";
    public static final String PARTY_CATALOG_ENTRY_TABLE = "hidra_party_catalog_entry";
    public static final String PARTY_CATALOG_TRANSLATION_TABLE = "hidra_party_catalog_translation";

    private PartyPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
