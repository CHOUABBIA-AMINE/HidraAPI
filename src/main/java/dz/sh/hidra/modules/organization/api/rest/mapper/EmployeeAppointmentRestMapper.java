/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.mapper
 *
 * @Description : Maps authorized detached results to explicit REST shapes and legacy current views.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.mapper;


import dz.sh.hidra.modules.organization.api.rest.response.EmployeeAppointmentResponse.*;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.domain.policy.EmployeeAppointmentPolicy.TypePair;
import java.util.ArrayList;
public final class EmployeeAppointmentRestMapper {
    private EmployeeAppointmentRestMapper() { }
    public static Assignment assignment(EmployeeAppointmentResult.PinnedVersion v) {
        if(v==null)return null;var a=v.snapshot().appointment().assignment();var b=v.snapshot().binding();
        return new Assignment(a.id(),a.employeeId(),v.companyPartyId(),a.organizationUnitId(),a.positionId(),
                a.assignmentType().name(),a.status().name(),a.validFrom(),a.validTo(),v.appointmentVersion(),b.id(),b.revision(),v.policyId(),v.policyRevision());
    }
    private static Occupancy occupancy(EmployeeAppointmentResult.PositionVersion v) {
        if(v==null)return null;
        return new Occupancy(v.assignmentId(),v.companyPartyId(),v.organizationUnitId(),v.positionId(),v.assignmentType().name(),v.status().name(),
                v.validFrom(),v.validTo(),v.appointmentVersion(),v.bindingId(),v.bindingRevision(),v.policyId(),v.policyRevision());
    }
    public static Mutation mutation(EmployeeAppointmentResult.MutationReceipt v) {
        return new Mutation(v.kind().name(),v.requestId(),v.eventId(),v.sampledAt(),v.historicalReplay(),assignment(v.before()),assignment(v.after()),assignment(v.successor()));
    }
    public static PrivateHistory privateHistory(EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent> v) {
        return new PrivateHistory(v.entries().stream().map(e->new PrivateEvent(e.eventId(),e.kind().name(),e.sampledAt(),assignment(e.before()),assignment(e.after()),assignment(e.successor()))).toList(),v.nextCursor(),v.hasNext());
    }
    public static PositionHistory positionHistory(EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PositionHistoryEvent> v) {
        return new PositionHistory(v.entries().stream().map(e->new PositionEvent(e.eventId(),e.kind().name(),e.sampledAt(),occupancy(e.before()),occupancy(e.after()),occupancy(e.successor()))).toList(),v.nextCursor(),v.hasNext());
    }
    public static Policy policy(EmployeeAppointmentResult.PolicyView v) {
        var matrix=new ArrayList<Overlap>();
        for(var first:AssignmentType.values())for(var second:AssignmentType.values())
            matrix.add(new Overlap(first.name(),second.name(),v.policy().overlapsAllowed().get(new TypePair(first,second))));
        return new Policy(v.companyPartyId(),v.organizationUnitId(),v.policyId(),v.revision(),v.policy().maxConcurrentPrimary(),v.policy().positionCapacity().name(),matrix);
    }
}
