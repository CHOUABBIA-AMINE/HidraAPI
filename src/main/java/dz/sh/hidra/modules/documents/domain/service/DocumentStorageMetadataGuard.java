/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentStorageMetadataGuard
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.domain.service
 *
 * @Description : Guards storage metadata safety rules.
 *
 */
package dz.sh.hidra.modules.documents.domain.service;

import dz.sh.hidra.modules.documents.domain.exception.DocumentsBoundaryViolationException;
import dz.sh.hidra.modules.documents.domain.policy.DocumentsBoundaryPolicy;

/**
 * Guards storage metadata safety rules.
 */
public class DocumentStorageMetadataGuard {

    public void ensureNoSecretStorageReference(String objectKey, String objectUri, String encryptionKeyReference) {
        if (DocumentsBoundaryPolicy.isForbiddenMetadataValue(objectKey)
                || DocumentsBoundaryPolicy.isForbiddenMetadataValue(objectUri)
                || DocumentsBoundaryPolicy.isForbiddenMetadataValue(encryptionKeyReference)) {
            throw new DocumentsBoundaryViolationException("Document metadata must not contain credentials, signed URLs, tokens, or key material.");
        }
    }
}
