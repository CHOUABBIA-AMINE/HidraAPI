/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyDiscoveryResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 * @Description : Exposes explicit authorized discovery pages without persistence entities.
 */
package dz.sh.hidra.modules.custody.api.rest.response;
import dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase.*;
import dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase.Receipt;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
public final class CustodyDiscoveryResponses {
    private CustodyDiscoveryResponses(){}
    public record TransferPointView(String id,String code,String companyId,String facilityId){
        static TransferPointView from(TransferPoint p){return new TransferPointView(p.id(),p.code(),p.companyId(),p.facilityId());}
    }
    public record PeriodView(String id,String periodCode,String agreementId,String transferPointId,
            String companyId,String status,Instant periodStart,Instant periodEnd){
        static PeriodView from(DiscoveredPeriod p){return new PeriodView(p.id(),p.periodCode(),p.agreementId(),
            p.transferPointId(),p.companyId(),p.status(),p.periodStart(),p.periodEnd());}
    }
    public record TransferPointPage(List<TransferPointView> items,int page,int size,long total,long totalPages){
        public static TransferPointPage from(DiscoveryPage<TransferPoint> p){
            return new TransferPointPage(p.items().stream().map(TransferPointView::from).toList(),p.page(),p.size(),p.total(),p.totalPages());
        }
    }
    public record PeriodPage(List<PeriodView> items,int page,int size,long total,long totalPages){
        public static PeriodPage from(DiscoveryPage<DiscoveredPeriod> p){
            return new PeriodPage(p.items().stream().map(PeriodView::from).toList(),p.page(),p.size(),p.total(),p.totalPages());
        }
    }
    public record MeasurementPage(List<CustodyMeasurementCaptureResponse> items,int page,int size,long total,long totalPages){
        public static MeasurementPage from(DiscoveryPage<Receipt> p){
            return new MeasurementPage(p.items().stream().map(CustodyMeasurementCaptureResponse::from).toList(),p.page(),p.size(),p.total(),p.totalPages());
        }
    }
    public record TicketPage(List<CustodyTransferLifecycleResponses.TicketView> items,int page,int size,long total,long totalPages){
        public static TicketPage from(DiscoveryPage<Ticket> p){
            return new TicketPage(p.items().stream().map(CustodyTransferLifecycleResponses.TicketView::from).toList(),p.page(),p.size(),p.total(),p.totalPages());
        }
    }

    public record AdmissionAgreementView(String id,String agreementNumber,String transferPointId,
            Instant validFrom,Instant validTo,String status) {
        static AdmissionAgreementView from(AdmissionAgreement a){
            return new AdmissionAgreementView(a.id(),a.agreementNumber(),a.transferPointId(),a.validFrom(),a.validTo(),a.status());
        }
    }
    public record AdmissionAgreementPage(List<AdmissionAgreementView> items,int page,int size,long total,long totalPages){
        public static AdmissionAgreementPage from(DiscoveryPage<AdmissionAgreement> p){
            return new AdmissionAgreementPage(p.items().stream().map(AdmissionAgreementView::from).toList(),
                p.page(),p.size(),p.total(),p.totalPages());
        }
    }
    public record PeriodAdmissionReceipt(String periodId,String companyId,String facilityId,String transferPointId,
            String agreementId,String periodCode,Instant periodStart,Instant periodEnd,String initialStatus,
            UUID commandId,String openedByActorId,Instant admittedAt,boolean replayed){
        public static PeriodAdmissionReceipt from(AdmissionReceipt a){
            return new PeriodAdmissionReceipt(a.periodId(),a.companyId(),a.facilityId(),a.transferPointId(),a.agreementId(),
                a.periodCode(),a.periodStart(),a.periodEnd(),a.initialStatus(),a.commandId(),a.openedByActorId(),a.admittedAt(),a.replayed());
        }
    }
}
