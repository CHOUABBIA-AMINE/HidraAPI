/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : InstalledCharacteristicLinkRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps validated protected draft links to explicit REST DTOs.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;
import dz.sh.hidra.modules.topology.api.rest.request.InstalledCharacteristicLinkRequests.Save;
import dz.sh.hidra.modules.topology.api.rest.response.InstalledCharacteristicLinkResponses;
import dz.sh.hidra.modules.topology.application.port.in.InstalledCharacteristicLinkUseCase;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import java.util.List;
import java.util.Objects;
public final class InstalledCharacteristicLinkRestMapper {
    private InstalledCharacteristicLinkRestMapper(){}
    public static InstalledCharacteristicLinkUseCase.Command save(String unit,String link,Save r){
        Objects.requireNonNull(r,"Body required");
        var item=new InstalledCharacteristicLink(link,Objects.requireNonNull(r.linkRevision()),
                r.companyPartyId(),unit,r.sourceId(),r.sourceRevisionId(),r.sourceSha256(),
                InstalledCharacteristicLink.Kind.valueOf(Objects.requireNonNull(r.kind())),
                r.effectiveFrom(),r.effectiveUntil(),r.recordedAt(),r.evidenceReference(),r.installationId());
        return new InstalledCharacteristicLinkUseCase.Command(
                InstalledCharacteristicLinkUseCase.Operation.SAVE_DRAFT,r.companyPartyId(),unit,link,
                item,r.requestId(),r.expectedEpoch(),null,0,null);
    }
    public static InstalledCharacteristicLinkUseCase.Command current(String unit,String link,String company){
        return new InstalledCharacteristicLinkUseCase.Command(
                InstalledCharacteristicLinkUseCase.Operation.READ_CURRENT,company,unit,link,
                null,null,null,null,0,null);
    }
    public static InstalledCharacteristicLinkUseCase.Command revision(String unit,String link,String company,Long rev){
        return new InstalledCharacteristicLinkUseCase.Command(
                InstalledCharacteristicLinkUseCase.Operation.READ_REVISION,company,unit,link,
                null,null,null,rev,0,null);
    }
    public static InstalledCharacteristicLinkUseCase.Command history(String unit,String link,String company,int limit,Long before){
        return new InstalledCharacteristicLinkUseCase.Command(
                InstalledCharacteristicLinkUseCase.Operation.LIST_HISTORY,company,unit,link,
                null,null,null,null,limit,before);
    }
    private static InstalledCharacteristicLinkResponses.Link from(InstalledCharacteristicLink v){
        if(v==null)return null;
        return new InstalledCharacteristicLinkResponses.Link(v.linkId(),v.linkRevision(),v.companyPartyId(),
                v.installedUnitId(),v.sourceId(),v.sourceRevisionId(),v.sourceSha256(),v.kind().name(),
                v.effectiveFrom(),v.effectiveUntil(),v.recordedAt(),v.evidenceReference(),v.installationId(),"DRAFT");
    }
    public static InstalledCharacteristicLinkResponses.Page from(InstalledCharacteristicLinkUseCase.Result v){
        return new InstalledCharacteristicLinkResponses.Page(from(v.link()),v.history().stream()
                .map(InstalledCharacteristicLinkRestMapper::from).toList(),v.replayed(),v.nextBeforeRevision());
    }
}
