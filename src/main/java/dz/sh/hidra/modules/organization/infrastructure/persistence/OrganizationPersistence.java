/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationPersistence
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.persistence
 *
 * @Description : Organization database table constants.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.persistence;

/**
 * Organization database table constants.
 */
public final class OrganizationPersistence {

    public static final String ORGANIZATION_UNIT_TYPE_TABLE = "hidra_org_unit_type";
    public static final String ORGANIZATION_UNIT_TYPE_TRANSLATION_TABLE = "hidra_org_unit_type_translation";
    public static final String ORGANIZATION_UNIT_TABLE = "hidra_org_unit";
    public static final String POSITION_TABLE = "hidra_org_position";
    public static final String EMPLOYEE_TABLE = "hidra_org_employee";
    public static final String ADMINISTRATIVE_STATE_TABLE = "hidra_org_administrative_state";
    public static final String ADMINISTRATIVE_DISTRICT_TABLE = "hidra_org_administrative_district";
    public static final String ADMINISTRATIVE_LOCALITY_TABLE = "hidra_org_administrative_locality";
    public static final String EMPLOYEE_ADDRESS_TABLE = "hidra_org_employee_address";
    public static final String EMPLOYEE_ASSIGNMENT_TABLE = "hidra_org_employee_assignment";
    public static final String REPORTING_LINE_TABLE = "hidra_org_reporting_line";
    public static final String RESPONSIBILITY_ASSIGNMENT_TABLE = "hidra_org_responsibility_assignment";
    public static final String ORGANIZATION_DELEGATION_TABLE = "hidra_org_delegation";
    public static final String SHIFT_TABLE = "hidra_org_shift";
    public static final String SHIFT_ASSIGNMENT_TABLE = "hidra_org_shift_assignment";
    public static final String ORGANIZATION_CONTACT_POINT_TABLE = "hidra_org_contact_point";
    public static final String ORGANIZATION_HIERARCHY_SNAPSHOT_TABLE = "hidra_org_hierarchy_snapshot";

    private OrganizationPersistence() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }
}
