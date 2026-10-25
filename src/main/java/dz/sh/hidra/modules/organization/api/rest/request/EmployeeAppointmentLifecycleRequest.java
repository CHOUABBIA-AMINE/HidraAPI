/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecycleRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.api.rest.request
 *
 * @Description : Defines strict appointment mutation bodies without caller authority.
 *
 */
package dz.sh.hidra.modules.organization.api.rest.request;


import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.annotation.JsonDeserialize;
import dz.sh.hidra.modules.organization.domain.value.AssignmentType;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import java.time.Instant;
import java.util.Set;

public final class EmployeeAppointmentLifecycleRequest {
    private EmployeeAppointmentLifecycleRequest() { }
    @JsonDeserialize(using=StrictEnd.class)
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentEndRequest",requiredProperties={"requestId","expectedVersion","endAt","reason"})
    public record End(String requestId,long expectedVersion,Instant endAt,String reason) { }
    @JsonDeserialize(using=StrictTransfer.class)
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentTransferRequest",requiredProperties={"requestId","expectedVersion","destinationOrganizationUnitId","destinationPositionId","destinationType","effectiveAt","expectedDestinationBindingRevision","expectedDestinationPolicyRevision","reason"})
    public record Transfer(String requestId,long expectedVersion,String destinationOrganizationUnitId,
            String destinationPositionId,AssignmentType destinationType,Instant effectiveAt,Instant successorValidTo,
            long expectedDestinationBindingRevision,long expectedDestinationPolicyRevision,String reason) { }
    @JsonDeserialize(using=StrictCorrect.class)
    @io.swagger.v3.oas.annotations.media.Schema(name="EmployeeAppointmentCorrectRequest",requiredProperties={"requestId","expectedVersion","correctedOrganizationUnitId","correctedPositionId","correctedType","correctedStatus","correctedValidFrom","expectedDestinationBindingRevision","expectedDestinationPolicyRevision","reason"})
    public record Correct(String requestId,long expectedVersion,String correctedOrganizationUnitId,
            String correctedPositionId,AssignmentType correctedType,AssignmentStatus correctedStatus,
            Instant correctedValidFrom,Instant correctedValidTo,long expectedDestinationBindingRevision,
            long expectedDestinationPolicyRevision,String reason) { }
    private static JsonNode object(JsonParser p,DeserializationContext c,Set<String> keys) {
        var n=c.readTree(p);
        if(n==null || !n.isObject())throw DatabindException.from(p,"Appointment object required");
        for(String key:n.propertyNames())if(!keys.contains(key))throw DatabindException.from(p,"Unsupported appointment field");
        return n;
    }
    private static String text(JsonParser p,JsonNode n,String key,boolean required,int maximum) {
        var v=n.get(key);if(v==null || v.isNull()) {if(required)throw DatabindException.from(p,"Required appointment field");return null;}
        if(!v.isString() || v.stringValue().length()>maximum)throw DatabindException.from(p,"Invalid appointment string");
        return v.stringValue();
    }
    private static long positive(JsonParser p,JsonNode n,String key) {
        var v=n.get(key);if(v==null || !v.isIntegralNumber())throw DatabindException.from(p,"Appointment integer required");
        try {long x=v.bigIntegerValue().longValueExact();if(x<=0)throw new ArithmeticException();return x;}
        catch(ArithmeticException e){throw DatabindException.from(p,"Appointment integer outside bounds");}
    }
    private static Instant time(JsonParser p,JsonNode n,String key,boolean required) {
        String v=text(p,n,key,required,40);if(v==null)return null;
        try {var at=Instant.parse(v);if(at.getNano()%1000!=0)throw new IllegalArgumentException();return at;}
        catch(RuntimeException e){throw DatabindException.from(p,"Exact appointment microseconds required");}
    }
    private static <T extends Enum<T>> T value(JsonParser p,JsonNode n,String key,Class<T> type,boolean required) {
        String v=text(p,n,key,required,40);if(v==null)return null;
        try{return Enum.valueOf(type,v);}catch(IllegalArgumentException e){throw DatabindException.from(p,"Invalid appointment enum");}
    }
    public static final class StrictLegacy extends ValueDeserializer<AssignEmployeeRequest> {
        @Override public AssignEmployeeRequest deserialize(JsonParser p,DeserializationContext c) {
            var n=object(p,c,Set.of("employeeId","organizationUnitId","positionId","assignmentType","validFrom","validTo","requestId"));
            return new AssignEmployeeRequest(text(p,n,"employeeId",true,80),text(p,n,"organizationUnitId",true,80),
                    text(p,n,"positionId",true,80),value(p,n,"assignmentType",AssignmentType.class,false),
                    time(p,n,"validFrom",false),time(p,n,"validTo",false),text(p,n,"requestId",false,80));
        }
    }
    public static final class StrictEnd extends ValueDeserializer<End> {
        @Override public End deserialize(JsonParser p,DeserializationContext c) {
            var n=object(p,c,Set.of("requestId","expectedVersion","endAt","reason"));
            return new End(text(p,n,"requestId",true,80),positive(p,n,"expectedVersion"),time(p,n,"endAt",true),text(p,n,"reason",true,2000));
        }
    }
    public static final class StrictTransfer extends ValueDeserializer<Transfer> {
        @Override public Transfer deserialize(JsonParser p,DeserializationContext c) {
            var n=object(p,c,Set.of("requestId","expectedVersion","destinationOrganizationUnitId","destinationPositionId","destinationType",
                    "effectiveAt","successorValidTo","expectedDestinationBindingRevision","expectedDestinationPolicyRevision","reason"));
            return new Transfer(text(p,n,"requestId",true,80),positive(p,n,"expectedVersion"),text(p,n,"destinationOrganizationUnitId",true,80),
                    text(p,n,"destinationPositionId",true,80),value(p,n,"destinationType",AssignmentType.class,true),time(p,n,"effectiveAt",true),
                    time(p,n,"successorValidTo",false),positive(p,n,"expectedDestinationBindingRevision"),positive(p,n,"expectedDestinationPolicyRevision"),text(p,n,"reason",true,2000));
        }
    }
    public static final class StrictCorrect extends ValueDeserializer<Correct> {
        @Override public Correct deserialize(JsonParser p,DeserializationContext c) {
            var n=object(p,c,Set.of("requestId","expectedVersion","correctedOrganizationUnitId","correctedPositionId","correctedType","correctedStatus",
                    "correctedValidFrom","correctedValidTo","expectedDestinationBindingRevision","expectedDestinationPolicyRevision","reason"));
            return new Correct(text(p,n,"requestId",true,80),positive(p,n,"expectedVersion"),text(p,n,"correctedOrganizationUnitId",true,80),
                    text(p,n,"correctedPositionId",true,80),value(p,n,"correctedType",AssignmentType.class,true),value(p,n,"correctedStatus",AssignmentStatus.class,true),
                    time(p,n,"correctedValidFrom",true),time(p,n,"correctedValidTo",false),positive(p,n,"expectedDestinationBindingRevision"),positive(p,n,"expectedDestinationPolicyRevision"),text(p,n,"reason",true,2000));
        }
    }
}
