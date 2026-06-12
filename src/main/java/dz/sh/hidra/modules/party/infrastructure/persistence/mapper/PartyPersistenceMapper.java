/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.mapper
 *
 * @Description : Maps party domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.party.domain.model.*;
import dz.sh.hidra.modules.party.infrastructure.persistence.entity.*;

/**
 * Maps party domain models to JPA entities.
 */
public final class PartyPersistenceMapper {

    private PartyPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static PartyJpaEntity toEntity(Party model) {
            return new PartyJpaEntity(
                        model.id(),
                        model.code(),
                        model.partyTypeId(),
                        model.legalName(),
                        model.tradeName(),
                        model.shortName(),
                        model.countryCode(),
                        model.jurisdictionCode(),
                        model.status(),
                        model.primaryRoleCodeSnapshot(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static Party toDomain(PartyJpaEntity entity) {
            return new Party(
                        entity.id(),
                        entity.code(),
                        entity.partyTypeId(),
                        entity.legalName(),
                        entity.tradeName(),
                        entity.shortName(),
                        entity.countryCode(),
                        entity.jurisdictionCode(),
                        entity.status(),
                        entity.primaryRoleCodeSnapshot(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyTypeJpaEntity toEntity(PartyType model) {
            return new PartyTypeJpaEntity(
                        model.id(),
                        model.code(),
                        model.regulatoryClassCode(),
                        model.description(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyType toDomain(PartyTypeJpaEntity entity) {
            return new PartyType(
                        entity.id(),
                        entity.code(),
                        entity.regulatoryClassCode(),
                        entity.description(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyTypeTranslationJpaEntity toEntity(PartyTypeTranslation model) {
            return new PartyTypeTranslationJpaEntity(
                        model.id(),
                        model.partyTypeId(),
                        model.languageCode(),
                        model.label(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyTypeTranslation toDomain(PartyTypeTranslationJpaEntity entity) {
            return new PartyTypeTranslation(
                        entity.id(),
                        entity.partyTypeId(),
                        entity.languageCode(),
                        entity.label(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyRoleJpaEntity toEntity(PartyRole model) {
            return new PartyRoleJpaEntity(
                        model.id(),
                        model.code(),
                        model.description(),
                        model.qualificationRequiredByDefault(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyRole toDomain(PartyRoleJpaEntity entity) {
            return new PartyRole(
                        entity.id(),
                        entity.code(),
                        entity.description(),
                        entity.qualificationRequiredByDefault(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyRoleTranslationJpaEntity toEntity(PartyRoleTranslation model) {
            return new PartyRoleTranslationJpaEntity(
                        model.id(),
                        model.partyRoleId(),
                        model.languageCode(),
                        model.label(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyRoleTranslation toDomain(PartyRoleTranslationJpaEntity entity) {
            return new PartyRoleTranslation(
                        entity.id(),
                        entity.partyRoleId(),
                        entity.languageCode(),
                        entity.label(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyRoleAssignmentJpaEntity toEntity(PartyRoleAssignment model) {
            return new PartyRoleAssignmentJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.roleId(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.qualificationRequired(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyRoleAssignment toDomain(PartyRoleAssignmentJpaEntity entity) {
            return new PartyRoleAssignment(
                        entity.id(),
                        entity.partyId(),
                        entity.roleId(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.qualificationRequired(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyLegalProfileJpaEntity toEntity(PartyLegalProfile model) {
            return new PartyLegalProfileJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.legalName(),
                        model.tradeName(),
                        model.legalFormCode(),
                        model.registrationSummary(),
                        model.jurisdictionCode(),
                        model.countryCode(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyLegalProfile toDomain(PartyLegalProfileJpaEntity entity) {
            return new PartyLegalProfile(
                        entity.id(),
                        entity.partyId(),
                        entity.legalName(),
                        entity.tradeName(),
                        entity.legalFormCode(),
                        entity.registrationSummary(),
                        entity.jurisdictionCode(),
                        entity.countryCode(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyRegistrationJpaEntity toEntity(PartyRegistration model) {
            return new PartyRegistrationJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.registrationType(),
                        model.registrationNumber(),
                        model.issuingAuthority(),
                        model.countryCode(),
                        model.issuedAt(),
                        model.expiresAt(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyRegistration toDomain(PartyRegistrationJpaEntity entity) {
            return new PartyRegistration(
                        entity.id(),
                        entity.partyId(),
                        entity.registrationType(),
                        entity.registrationNumber(),
                        entity.issuingAuthority(),
                        entity.countryCode(),
                        entity.issuedAt(),
                        entity.expiresAt(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyTaxIdentifierJpaEntity toEntity(PartyTaxIdentifier model) {
            return new PartyTaxIdentifierJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.taxIdentifierType(),
                        model.identifierValue(),
                        model.countryCode(),
                        model.primaryIdentifier(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyTaxIdentifier toDomain(PartyTaxIdentifierJpaEntity entity) {
            return new PartyTaxIdentifier(
                        entity.id(),
                        entity.partyId(),
                        entity.taxIdentifierType(),
                        entity.identifierValue(),
                        entity.countryCode(),
                        entity.primaryIdentifier(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyAddressJpaEntity toEntity(PartyAddress model) {
            return new PartyAddressJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.addressType(),
                        model.countryCode(),
                        model.stateOrRegion(),
                        model.city(),
                        model.postalCode(),
                        model.addressLine1(),
                        model.addressLine2(),
                        model.primaryAddress(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyAddress toDomain(PartyAddressJpaEntity entity) {
            return new PartyAddress(
                        entity.id(),
                        entity.partyId(),
                        entity.addressType(),
                        entity.countryCode(),
                        entity.stateOrRegion(),
                        entity.city(),
                        entity.postalCode(),
                        entity.addressLine1(),
                        entity.addressLine2(),
                        entity.primaryAddress(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyContactPointJpaEntity toEntity(PartyContactPoint model) {
            return new PartyContactPointJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.contactPointType(),
                        model.label(),
                        model.value(),
                        model.primaryContact(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyContactPoint toDomain(PartyContactPointJpaEntity entity) {
            return new PartyContactPoint(
                        entity.id(),
                        entity.partyId(),
                        entity.contactPointType(),
                        entity.label(),
                        entity.value(),
                        entity.primaryContact(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyContactPersonJpaEntity toEntity(PartyContactPerson model) {
            return new PartyContactPersonJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.fullName(),
                        model.jobTitle(),
                        model.emailAddress(),
                        model.phoneNumber(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyContactPerson toDomain(PartyContactPersonJpaEntity entity) {
            return new PartyContactPerson(
                        entity.id(),
                        entity.partyId(),
                        entity.fullName(),
                        entity.jobTitle(),
                        entity.emailAddress(),
                        entity.phoneNumber(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyBankReferenceJpaEntity toEntity(PartyBankReference model) {
            return new PartyBankReferenceJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.bankName(),
                        model.accountReferenceMasked(),
                        model.ibanMasked(),
                        model.swiftCode(),
                        model.countryCode(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyBankReference toDomain(PartyBankReferenceJpaEntity entity) {
            return new PartyBankReference(
                        entity.id(),
                        entity.partyId(),
                        entity.bankName(),
                        entity.accountReferenceMasked(),
                        entity.ibanMasked(),
                        entity.swiftCode(),
                        entity.countryCode(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyQualificationJpaEntity toEntity(PartyQualification model) {
            return new PartyQualificationJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.qualificationTypeCode(),
                        model.qualificationStatus(),
                        model.approvedFrom(),
                        model.approvedTo(),
                        model.approvalReferenceId(),
                        model.riskLevelSnapshot(),
                        model.lastReviewDate(),
                        model.nextReviewDate(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyQualification toDomain(PartyQualificationJpaEntity entity) {
            return new PartyQualification(
                        entity.id(),
                        entity.partyId(),
                        entity.qualificationTypeCode(),
                        entity.qualificationStatus(),
                        entity.approvedFrom(),
                        entity.approvedTo(),
                        entity.approvalReferenceId(),
                        entity.riskLevelSnapshot(),
                        entity.lastReviewDate(),
                        entity.nextReviewDate(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static SupplierQualificationJpaEntity toEntity(SupplierQualification model) {
            return new SupplierQualificationJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.supplierCategoryCode(),
                        model.qualificationStatus(),
                        model.approvedFrom(),
                        model.approvedTo(),
                        model.approvalReferenceId(),
                        model.riskLevelSnapshot(),
                        model.lastReviewDate(),
                        model.nextReviewDate(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static SupplierQualification toDomain(SupplierQualificationJpaEntity entity) {
            return new SupplierQualification(
                        entity.id(),
                        entity.partyId(),
                        entity.supplierCategoryCode(),
                        entity.qualificationStatus(),
                        entity.approvedFrom(),
                        entity.approvedTo(),
                        entity.approvalReferenceId(),
                        entity.riskLevelSnapshot(),
                        entity.lastReviewDate(),
                        entity.nextReviewDate(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static VendorQualificationJpaEntity toEntity(VendorQualification model) {
            return new VendorQualificationJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.vendorCategoryCode(),
                        model.qualificationStatus(),
                        model.approvedFrom(),
                        model.approvedTo(),
                        model.approvalReferenceId(),
                        model.riskLevelSnapshot(),
                        model.lastReviewDate(),
                        model.nextReviewDate(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static VendorQualification toDomain(VendorQualificationJpaEntity entity) {
            return new VendorQualification(
                        entity.id(),
                        entity.partyId(),
                        entity.vendorCategoryCode(),
                        entity.qualificationStatus(),
                        entity.approvedFrom(),
                        entity.approvedTo(),
                        entity.approvalReferenceId(),
                        entity.riskLevelSnapshot(),
                        entity.lastReviewDate(),
                        entity.nextReviewDate(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ContractorQualificationJpaEntity toEntity(ContractorQualification model) {
            return new ContractorQualificationJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.contractorCategoryCode(),
                        model.qualificationStatus(),
                        model.approvedFrom(),
                        model.approvedTo(),
                        model.approvalReferenceId(),
                        model.riskLevelSnapshot(),
                        model.lastReviewDate(),
                        model.nextReviewDate(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ContractorQualification toDomain(ContractorQualificationJpaEntity entity) {
            return new ContractorQualification(
                        entity.id(),
                        entity.partyId(),
                        entity.contractorCategoryCode(),
                        entity.qualificationStatus(),
                        entity.approvedFrom(),
                        entity.approvedTo(),
                        entity.approvalReferenceId(),
                        entity.riskLevelSnapshot(),
                        entity.lastReviewDate(),
                        entity.nextReviewDate(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ManufacturerProfileJpaEntity toEntity(ManufacturerProfile model) {
            return new ManufacturerProfileJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.capabilityType(),
                        model.brandName(),
                        model.manufacturerCode(),
                        model.qualificationStatus(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ManufacturerProfile toDomain(ManufacturerProfileJpaEntity entity) {
            return new ManufacturerProfile(
                        entity.id(),
                        entity.partyId(),
                        entity.capabilityType(),
                        entity.brandName(),
                        entity.manufacturerCode(),
                        entity.qualificationStatus(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OwnerProfileJpaEntity toEntity(OwnerProfile model) {
            return new OwnerProfileJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.ownerProfileType(),
                        model.ownershipContextCode(),
                        model.riskLevelSnapshot(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OwnerProfile toDomain(OwnerProfileJpaEntity entity) {
            return new OwnerProfile(
                        entity.id(),
                        entity.partyId(),
                        entity.ownerProfileType(),
                        entity.ownershipContextCode(),
                        entity.riskLevelSnapshot(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static OperatorProfileJpaEntity toEntity(OperatorProfile model) {
            return new OperatorProfileJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.capabilityType(),
                        model.operatorCode(),
                        model.qualificationStatus(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static OperatorProfile toDomain(OperatorProfileJpaEntity entity) {
            return new OperatorProfile(
                        entity.id(),
                        entity.partyId(),
                        entity.capabilityType(),
                        entity.operatorCode(),
                        entity.qualificationStatus(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyCertificationJpaEntity toEntity(PartyCertification model) {
            return new PartyCertificationJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.certificationCode(),
                        model.certificationBodyPartyId(),
                        model.certificateNumber(),
                        model.issuedAt(),
                        model.expiresAt(),
                        model.status(),
                        model.documentReferenceId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyCertification toDomain(PartyCertificationJpaEntity entity) {
            return new PartyCertification(
                        entity.id(),
                        entity.partyId(),
                        entity.certificationCode(),
                        entity.certificationBodyPartyId(),
                        entity.certificateNumber(),
                        entity.issuedAt(),
                        entity.expiresAt(),
                        entity.status(),
                        entity.documentReferenceId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyComplianceStatusJpaEntity toEntity(PartyComplianceStatus model) {
            return new PartyComplianceStatusJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.complianceStatus(),
                        model.screeningSource(),
                        model.screeningReference(),
                        model.checkedAt(),
                        model.validUntil(),
                        model.notes(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyComplianceStatus toDomain(PartyComplianceStatusJpaEntity entity) {
            return new PartyComplianceStatus(
                        entity.id(),
                        entity.partyId(),
                        entity.complianceStatus(),
                        entity.screeningSource(),
                        entity.screeningReference(),
                        entity.checkedAt(),
                        entity.validUntil(),
                        entity.notes(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyRiskSnapshotJpaEntity toEntity(PartyRiskSnapshot model) {
            return new PartyRiskSnapshotJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.riskLevel(),
                        model.riskSourceModule(),
                        model.riskSourceReferenceId(),
                        model.riskReason(),
                        model.assessedAt(),
                        model.validUntil(),
                        model.createdAt()
            );
        }

        public static PartyRiskSnapshot toDomain(PartyRiskSnapshotJpaEntity entity) {
            return new PartyRiskSnapshot(
                        entity.id(),
                        entity.partyId(),
                        entity.riskLevel(),
                        entity.riskSourceModule(),
                        entity.riskSourceReferenceId(),
                        entity.riskReason(),
                        entity.assessedAt(),
                        entity.validUntil(),
                        entity.createdAt()
            );
        }

        public static PartyRelationshipJpaEntity toEntity(PartyRelationship model) {
            return new PartyRelationshipJpaEntity(
                        model.id(),
                        model.sourcePartyId(),
                        model.targetPartyId(),
                        model.relationshipType(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyRelationship toDomain(PartyRelationshipJpaEntity entity) {
            return new PartyRelationship(
                        entity.id(),
                        entity.sourcePartyId(),
                        entity.targetPartyId(),
                        entity.relationshipType(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyOwnershipLinkJpaEntity toEntity(PartyOwnershipLink model) {
            return new PartyOwnershipLinkJpaEntity(
                        model.id(),
                        model.ownerPartyId(),
                        model.targetType(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetNameSnapshot(),
                        model.ownershipPercentage(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyOwnershipLink toDomain(PartyOwnershipLinkJpaEntity entity) {
            return new PartyOwnershipLink(
                        entity.id(),
                        entity.ownerPartyId(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetNameSnapshot(),
                        entity.ownershipPercentage(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyDocumentReferenceJpaEntity toEntity(PartyDocumentReference model) {
            return new PartyDocumentReferenceJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.documentReferenceType(),
                        model.documentId(),
                        model.documentCodeSnapshot(),
                        model.documentTitleSnapshot(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyDocumentReference toDomain(PartyDocumentReferenceJpaEntity entity) {
            return new PartyDocumentReference(
                        entity.id(),
                        entity.partyId(),
                        entity.documentReferenceType(),
                        entity.documentId(),
                        entity.documentCodeSnapshot(),
                        entity.documentTitleSnapshot(),
                        entity.validFrom(),
                        entity.validTo(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyExternalReferenceJpaEntity toEntity(PartyExternalReference model) {
            return new PartyExternalReferenceJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.externalSystemType(),
                        model.externalSystemCode(),
                        model.externalReference(),
                        model.externalLabelSnapshot(),
                        model.status(),
                        model.lastSynchronizedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyExternalReference toDomain(PartyExternalReferenceJpaEntity entity) {
            return new PartyExternalReference(
                        entity.id(),
                        entity.partyId(),
                        entity.externalSystemType(),
                        entity.externalSystemCode(),
                        entity.externalReference(),
                        entity.externalLabelSnapshot(),
                        entity.status(),
                        entity.lastSynchronizedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyStatusHistoryJpaEntity toEntity(PartyStatusHistory model) {
            return new PartyStatusHistoryJpaEntity(
                        model.id(),
                        model.partyId(),
                        model.oldStatus(),
                        model.newStatus(),
                        model.reason(),
                        model.reasonMessage(),
                        model.changedByActorId(),
                        model.changedAt(),
                        model.correlationId()
            );
        }

        public static PartyStatusHistory toDomain(PartyStatusHistoryJpaEntity entity) {
            return new PartyStatusHistory(
                        entity.id(),
                        entity.partyId(),
                        entity.oldStatus(),
                        entity.newStatus(),
                        entity.reason(),
                        entity.reasonMessage(),
                        entity.changedByActorId(),
                        entity.changedAt(),
                        entity.correlationId()
            );
        }

        public static PartyCatalogEntryJpaEntity toEntity(PartyCatalogEntry model) {
            return new PartyCatalogEntryJpaEntity(
                        model.id(),
                        model.catalogCode(),
                        model.entryCode(),
                        model.parentEntryId(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyCatalogEntry toDomain(PartyCatalogEntryJpaEntity entity) {
            return new PartyCatalogEntry(
                        entity.id(),
                        entity.catalogCode(),
                        entity.entryCode(),
                        entity.parentEntryId(),
                        entity.status(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static PartyCatalogTranslationJpaEntity toEntity(PartyCatalogTranslation model) {
            return new PartyCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.languageCode(),
                        model.label(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static PartyCatalogTranslation toDomain(PartyCatalogTranslationJpaEntity entity) {
            return new PartyCatalogTranslation(
                        entity.id(),
                        entity.catalogEntryId(),
                        entity.languageCode(),
                        entity.label(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

}
