/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.response
 * @Description : Collision-free controlled Documents discovery, lineage and decision response schemas.
 */
package dz.sh.hidra.modules.documents.api.rest.response;

import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

public final class ControlledDocumentResponses {
    private ControlledDocumentResponses(){}
    @Schema(name="ControlledDocumentDetail")
    public record Detail(String id,String code,String titleFr,String companyId,String facilityId,
            String status,String currentVersionId,String currentChecksum,Instant createdAt) {
        public static Detail from(DocumentView d){
            return new Detail(d.id(),d.code(),d.titleFr(),d.companyId(),d.facilityId(),
                    d.status(),d.currentVersionId(),d.currentChecksum(),d.createdAt());
        }
    }
    @Schema(name="ControlledDocumentDiscovery")
    public record Discovery(List<Detail> items,long total,int page,int size) {
        public static Discovery from(Page<DocumentView> p){
            return new Discovery(p.items().stream().map(Detail::from).toList(),p.total(),p.page(),p.size());
        }
    }
    @Schema(name="ControlledDocumentVersion")
    public record Version(String id,int number,String status,String checksum,long length,
            String uploadedBy,Instant uploadedAt,String supersededBy){
        public static Version from(VersionView v){return new Version(v.id(),v.number(),v.status(),
                v.checksum(),v.length(),v.uploadedBy(),v.uploadedAt(),v.supersededBy());}
    }
    @Schema(name="ControlledDocumentVersions")
    public record Versions(List<Version> items,long total,int page,int size){
        public static Versions from(Page<VersionView> p){
            return new Versions(p.items().stream().map(Version::from).toList(),p.total(),p.page(),p.size());
        }
    }
    @Schema(name="ControlledDocumentLink")
    public record Link(String id,String versionId,String targetModule,String targetType,String targetId,
            String roleId,boolean primary,Instant linkedAt){
        public static Link from(LinkView v){return new Link(v.id(),v.versionId(),
                v.targetModule(),v.targetType(),v.targetId(),v.roleId(),v.primary(),v.linkedAt());}
    }
    @Schema(name="ControlledDocumentLinks")
    public record Links(List<Link> items,long total,int page,int size){
        public static Links from(Page<LinkView> p){
            return new Links(p.items().stream().map(Link::from).toList(),p.total(),p.page(),p.size());
        }
    }
    @Schema(name="ControlledDocumentCreate")
    public record Created(Detail document,boolean replayed) {
        public static Created from(CreateResult r){return new Created(Detail.from(r.document()),r.replayed());}
    }
    @Schema(name="ControlledDocumentDecision")
    public record Decision(String id,String documentId,String versionId,String previousVersionId,
            String action,String requestId,String checksumSha256,long contentLength,
            String companyId,String facilityId,String linkId,Instant decidedAt,boolean replayed){
        public static Decision from(DecisionResult r){
            var d=r.decision();
            return new Decision(d.id(),d.documentId(),d.versionId(),d.previousVersionId(),
                    d.action(),d.requestId(),d.checksumSha256(),d.contentLength(),
                    d.companyId(),d.facilityId(),d.linkId(),d.decidedAt(),r.replayed());
        }
    }
}
