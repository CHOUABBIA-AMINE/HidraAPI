/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.response
 *
 * @Description : Carries detached authorized appointment and separately redacted occupancy representations.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.response;


import java.time.Instant;
import java.util.List;
public final class EmployeeAppointmentResponse {
    private EmployeeAppointmentResponse() { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentAssignmentResponse")
    public record Assignment(String id,String employeeId,String companyPartyId,String organizationUnitId,String positionId,
            String assignmentType,String status,Instant validFrom,Instant validTo,long version,
            String bindingId,long bindingRevision,String policyId,long policyRevision) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentOccupancyResponse")
    public record Occupancy(String id,String companyPartyId,String organizationUnitId,String positionId,
            String assignmentType,String status,Instant validFrom,Instant validTo,long version,
            String bindingId,long bindingRevision,String policyId,long policyRevision) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentMutationResponse")
    public record Mutation(String kind,String requestId,String eventId,Instant sampledAt,boolean historicalReplay,
            Assignment before,Assignment after,Assignment successor) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentPrivateEventResponse")
    public record PrivateEvent(String eventId,String kind,Instant sampledAt,Assignment before,Assignment after,Assignment successor) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentPositionEventResponse")
    public record PositionEvent(String eventId,String kind,Instant sampledAt,Occupancy before,Occupancy after,Occupancy successor) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentPrivateHistoryResponse")
    public record PrivateHistory(List<PrivateEvent> entries,String nextCursor,boolean hasNext) {
        public PrivateHistory { entries=List.copyOf(entries); }
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentPositionHistoryResponse")
    public record PositionHistory(List<PositionEvent> entries,String nextCursor,boolean hasNext) {
        public PositionHistory { entries=List.copyOf(entries); }
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentOverlapResponse")
    public record Overlap(String first,String second,boolean allowed) { }
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentPolicyResponse")
    public record Policy(String companyPartyId,String organizationUnitId,String policyId,long revision,
            int maxConcurrentPrimary,String positionCapacity,List<Overlap> overlapsAllowed) {
        public Policy { overlapsAllowed=List.copyOf(overlapsAllowed); }
    }
}
