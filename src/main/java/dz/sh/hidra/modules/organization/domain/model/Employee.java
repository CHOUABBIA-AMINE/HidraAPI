/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Employee
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.model
 *
 * @Description : Operational employee/person with structured names, birth data and employment lifecycle.
 *
 */
package dz.sh.hidra.modules.organization.domain.model;

import dz.sh.hidra.modules.organization.domain.exception.InvalidOrganizationValueException;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * Operational employee/person, distinct from an identity/login user.
 *
 * <p>Business role: owns stable employee identity, structured Arabic/Latin names,
 * optional authoritative birth information, employment classification/lifecycle and
 * an optional neutral reference to the identity module.</p>
 *
 * <p>Architecture role: this record deliberately does not own postal addresses or
 * operational communication channels. Those belong to {@link EmployeeAddress} and
 * {@link OrganizationContactPoint}. The direct email/mobile record components are
 * retained only as deprecated compatibility state until persistence/API consumers are
 * migrated; they must not be treated as the canonical contact source.</p>
 *
 * <p>Name semantics: structured {@code firstNameAr/lastNameAr} and
 * {@code firstNameLt/lastNameLt} are canonical. Display names are derived through
 * {@link #arabicDisplayName()} and {@link #latinDisplayName()}. The historical
 * {@code displayNameAr/displayNameLt} record components remain compatibility-only until
 * persistence/API cutover proves they can be removed safely.</p>
 *
 * <p>Validation: employee ID, employee number, employee type and lifecycle status are
 * required. Birth date cannot be in the future. A termination timestamp requires a
 * hire timestamp and cannot precede it. Optional text is trimmed and blank values
 * become {@code null}.</p>
 *
 * <p>Usage: {@code birthLocalityId} is an optional normalized reference when the
 * birthplace maps to the current Algerian administrative hierarchy. The multilingual
 * birthplace fields preserve authoritative/free-text place names independently, so no
 * fake locality record is required for foreign or historical places.</p>
 *
 * @param id employee domain identifier
 * @param employeeNumber stable employee/business number
 * @param firstNameAr Arabic first name
 * @param lastNameAr Arabic last name
 * @param firstNameLt Latin-transliterated first name
 * @param lastNameLt Latin-transliterated last name
 * @param displayNameAr deprecated compatibility Arabic display value; canonical display name is derived
 * @param displayNameLt deprecated compatibility Latin display value; canonical display name is derived
 * @param dateOfBirth optional date of birth
 * @param birthLocalityId optional normalized AdministrativeLocality identifier
 * @param birthPlaceAr optional authoritative/free-text Arabic birthplace
 * @param birthPlaceFr optional authoritative/free-text French birthplace
 * @param birthPlaceEn optional authoritative/free-text English birthplace
 * @param emailAddress deprecated compatibility email; canonical contact data belongs to OrganizationContactPoint
 * @param mobileNumber deprecated compatibility mobile; canonical contact data belongs to OrganizationContactPoint
 * @param employeeType employment classification
 * @param status employee lifecycle status
 * @param identityUserReference optional neutral identity-user reference
 * @param hiredAt optional hire timestamp
 * @param terminatedAt optional termination timestamp
 * @param createdAt creation timestamp
 * @param updatedAt last update timestamp
 */
public record Employee(
        String id,
        String employeeNumber,
        String firstNameAr,
        String lastNameAr,
        String firstNameLt,
        String lastNameLt,
        String displayNameAr,
        String displayNameLt,
        LocalDate dateOfBirth,
        String birthLocalityId,
        String birthPlaceAr,
        String birthPlaceFr,
        String birthPlaceEn,
        String emailAddress,
        String mobileNumber,
        EmployeeType employeeType,
        EmployeeStatus status,
        String identityUserReference,
        Instant hiredAt,
        Instant terminatedAt,
        Instant createdAt,
        Instant updatedAt
) {

    public Employee {
        id = requireText(id, "Employee ID must not be null or blank.");
        employeeNumber = requireText(
                employeeNumber,
                "Employee number must not be null or blank."
        );
        firstNameAr = normalize(firstNameAr);
        lastNameAr = normalize(lastNameAr);
        firstNameLt = normalize(firstNameLt);
        lastNameLt = normalize(lastNameLt);
        displayNameAr = normalize(displayNameAr);
        displayNameLt = normalize(displayNameLt);
        birthLocalityId = normalize(birthLocalityId);
        birthPlaceAr = normalize(birthPlaceAr);
        birthPlaceFr = normalize(birthPlaceFr);
        birthPlaceEn = normalize(birthPlaceEn);
        emailAddress = normalize(emailAddress);
        mobileNumber = normalize(mobileNumber);
        identityUserReference = normalize(identityUserReference);

        if (employeeType == null) {
            throw new InvalidOrganizationValueException(
                    "Employee type must not be null."
            );
        }
        if (status == null) {
            throw new InvalidOrganizationValueException(
                    "Employee status must not be null."
            );
        }
        if (dateOfBirth != null && dateOfBirth.isAfter(LocalDate.now(ZoneOffset.UTC))) {
            throw new InvalidOrganizationValueException(
                    "Employee date of birth must not be in the future."
            );
        }
        if (terminatedAt != null && hiredAt == null) {
            throw new InvalidOrganizationValueException(
                    "Employee termination timestamp requires a hire timestamp."
            );
        }
        if (terminatedAt != null && terminatedAt.isBefore(hiredAt)) {
            throw new InvalidOrganizationValueException(
                    "Employee termination timestamp must not precede hire timestamp."
            );
        }
    }

    /**
     * Compatibility constructor for existing application/persistence callers while
     * ORG-050 remains intentionally domain-only.
     *
     * <p>Birth data defaults to {@code null}; later persistence/API cutover tasks must
     * expose and store it explicitly rather than inferring values.</p>
     */
    @Deprecated(forRemoval = true)
    public Employee(
            String id,
            String employeeNumber,
            String firstNameAr,
            String lastNameAr,
            String firstNameLt,
            String lastNameLt,
            String displayNameAr,
            String displayNameLt,
            String emailAddress,
            String mobileNumber,
            EmployeeType employeeType,
            EmployeeStatus status,
            String identityUserReference,
            Instant hiredAt,
            Instant terminatedAt,
            Instant createdAt,
            Instant updatedAt
    ) {
        this(
                id,
                employeeNumber,
                firstNameAr,
                lastNameAr,
                firstNameLt,
                lastNameLt,
                displayNameAr,
                displayNameLt,
                null,
                null,
                null,
                null,
                null,
                emailAddress,
                mobileNumber,
                employeeType,
                status,
                identityUserReference,
                hiredAt,
                terminatedAt,
                createdAt,
                updatedAt
        );
    }

    /**
     * Returns the canonical Arabic display name derived from structured Arabic names.
     *
     * @return joined Arabic first/last name, or null when both are absent
     */
    public String arabicDisplayName() {
        return join(firstNameAr, lastNameAr);
    }

    /**
     * Returns the canonical Latin display name derived from structured Latin names.
     *
     * @return joined Latin first/last name, or null when both are absent
     */
    public String latinDisplayName() {
        return join(firstNameLt, lastNameLt);
    }

    /**
     * Compatibility accessor for the historical persisted Arabic display value.
     *
     * <p>New domain consumers must use {@link #arabicDisplayName()}.</p>
     *
     * @return historical compatibility value, if populated
     */
    @Deprecated(forRemoval = true)
    @Override
    public String displayNameAr() {
        return displayNameAr;
    }

    /**
     * Compatibility accessor for the historical persisted Latin display value.
     *
     * <p>New domain consumers must use {@link #latinDisplayName()}.</p>
     *
     * @return historical compatibility value, if populated
     */
    @Deprecated(forRemoval = true)
    @Override
    public String displayNameLt() {
        return displayNameLt;
    }

    /**
     * Transitional compatibility accessor.
     *
     * <p>Canonical employee email contact is modeled by
     * {@link OrganizationContactPoint} with target type EMPLOYEE and contact type EMAIL.</p>
     *
     * @return legacy direct employee email, if still populated
     */
    @Deprecated(forRemoval = true)
    @Override
    public String emailAddress() {
        return emailAddress;
    }

    /**
     * Transitional compatibility accessor.
     *
     * <p>Canonical employee mobile contact is modeled by
     * {@link OrganizationContactPoint} with target type EMPLOYEE and contact type MOBILE.</p>
     *
     * @return legacy direct employee mobile number, if still populated
     */
    @Deprecated(forRemoval = true)
    @Override
    public String mobileNumber() {
        return mobileNumber;
    }

    private static String join(String firstName, String lastName) {
        String first = normalize(firstName);
        String last = normalize(lastName);
        if (first == null) {
            return last;
        }
        if (last == null) {
            return first;
        }
        return first + " " + last;
    }

    private static String requireText(String value, String message) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new InvalidOrganizationValueException(message);
        }
        return normalized;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
