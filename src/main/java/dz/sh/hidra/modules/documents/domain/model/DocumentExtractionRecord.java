/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExtractionRecord
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.model
 *
 * @Description : Derived OCR/text/metadata extraction record.
 *
 */
package dz.sh.hidra.modules.documents.domain.model;

import dz.sh.hidra.modules.documents.domain.exception.InvalidDocumentValueException;
import dz.sh.hidra.modules.documents.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Derived OCR/text/metadata extraction record.
     *
         * @param id id
     * @param documentVersionId documentVersionId
     * @param extractionType extractionType
     * @param extractionStatus extractionStatus
     * @param extractedTextRef extractedTextRef
     * @param extractedMetadataJson extractedMetadataJson
     * @param confidenceScore confidenceScore
     * @param languageDetected languageDetected
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param failureReason failureReason
     */
    public record DocumentExtractionRecord(
            String id,
        String documentVersionId,
        DocumentExtractionType extractionType,
        DocumentExtractionStatus extractionStatus,
        String extractedTextRef,
        String extractedMetadataJson,
        BigDecimal confidenceScore,
        String languageDetected,
        Instant startedAt,
        Instant completedAt,
        String failureReason
    ) {

        public DocumentExtractionRecord {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidDocumentValueException("DocumentExtractionRecord id must not be blank.");
        }
        // HRA-051 required: documentVersionId
        if (documentVersionId == null || documentVersionId.isBlank()) {
            throw new InvalidDocumentValueException("DocumentExtractionRecord document version id must not be blank.");
        }
        // HRA-051 required: extractionType
        if (extractionType == null) {
            throw new InvalidDocumentValueException("DocumentExtractionRecord extraction type must not be null.");
        }
        // HRA-051 required: extractionStatus
        if (extractionStatus == null) {
            throw new InvalidDocumentValueException("DocumentExtractionRecord extraction status must not be null.");
        }

        id = normalize(id);
        documentVersionId = normalize(documentVersionId);
        extractedTextRef = normalize(extractedTextRef);
        extractedMetadataJson = normalize(extractedMetadataJson);
        languageDetected = normalize(languageDetected);
        failureReason = normalize(failureReason);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
