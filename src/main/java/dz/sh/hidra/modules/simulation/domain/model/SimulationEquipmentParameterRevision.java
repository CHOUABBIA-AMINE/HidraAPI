/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationEquipmentParameterRevision
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.domain.model
 *
 * @Description : Defines immutable supplied compressor, valve and regulator parameters with independent source provenance.
 *
 */
package dz.sh.hidra.modules.simulation.domain.model;

import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

/** Supplied source facts only; no interpolation, hydraulic computation or operational approval. */
public record SimulationEquipmentParameterRevision(String sourceId, String revisionId, Instant recordedAt,
        Instant effectiveFrom, Instant effectiveUntil, Origin origin, String evidenceReference,
        String networkSourceId, String networkRevisionId, String networkSha256,
        String fluidSourceId, String fluidRevisionId, String fluidSha256, String fluidQualificationId,
        List<Equipment> equipment, List<CompressorCurve> compressorCurves,
        List<ValveCharacteristic> valveCharacteristics, List<GovernedLimit> governedLimits,
        GovernanceBinding governanceBinding, List<SimulationRegulatorCharacteristicRevision> regulatorCharacteristics) {
    public enum Origin { DECLARED_PARAMETER, SYNTHETIC }
    public enum Kind { COMPRESSOR, VALVE, REGULATOR }
    public enum Quantity { INLET_PRESSURE_PASCALS_ABSOLUTE, INLET_TEMPERATURE_KELVIN,
        MASS_FLOW_KILOGRAMS_PER_SECOND, ROTATIONAL_SPEED_REVOLUTIONS_PER_MINUTE, OPENING_FRACTION, DOWNSTREAM_SETPOINT_PRESSURE_PASCALS_ABSOLUTE }
    public SimulationEquipmentParameterRevision(String sourceId,String revisionId,Instant recordedAt,Instant effectiveFrom,Instant effectiveUntil,Origin origin,String evidenceReference,
            String networkSourceId,String networkRevisionId,String networkSha256,String fluidSourceId,String fluidRevisionId,String fluidSha256,String fluidQualificationId,
            List<Equipment> equipment,List<CompressorCurve> compressorCurves,List<ValveCharacteristic> valveCharacteristics,List<GovernedLimit> governedLimits,GovernanceBinding governanceBinding) {
        this(sourceId,revisionId,recordedAt,effectiveFrom,effectiveUntil,origin,evidenceReference,networkSourceId,networkRevisionId,networkSha256,fluidSourceId,fluidRevisionId,fluidSha256,fluidQualificationId,equipment,compressorCurves,valveCharacteristics,governedLimits,governanceBinding,List.of());
    }
    public enum ControlMode { DOWNSTREAM_SETPOINT }
    public enum Isolation { OPEN, CLOSED }
    public record RegulatorConfiguration(String characteristicId,String characteristicRevisionId,String method,
            ControlMode controlMode,Isolation isolation,BigDecimal downstreamSetpointPascalsAbsolute) {
        public RegulatorConfiguration {
            characteristicId=text(characteristicId);characteristicRevisionId=text(characteristicRevisionId);
            require("SYNTHETIC_REGULATOR_DOWNSTREAM_SETPOINT_CAPACITY_V1".equals(method) && controlMode==ControlMode.DOWNSTREAM_SETPOINT && isolation!=null,"Explicit synthetic regulator configuration required.");
            positive(downstreamSetpointPascalsAbsolute);
        }
    }
    public SimulationEquipmentParameterRevision {
        sourceId=text(sourceId);revisionId=text(revisionId);evidenceReference=text(evidenceReference);
        networkSourceId=text(networkSourceId);networkRevisionId=text(networkRevisionId);networkSha256=digest(networkSha256);
        fluidSourceId=text(fluidSourceId);fluidRevisionId=text(fluidRevisionId);fluidSha256=digest(fluidSha256);fluidQualificationId=text(fluidQualificationId);
        interval(recordedAt,effectiveFrom,effectiveUntil);require(origin!=null && governanceBinding!=null,"Complete source governance required.");
        equipment=copy(equipment);compressorCurves=copy(compressorCurves);valveCharacteristics=copy(valveCharacteristics);governedLimits=copy(governedLimits);regulatorCharacteristics=copy(regulatorCharacteristics);
        var curves=new HashMap<String,CompressorCurve>();var valves=new HashMap<String,ValveCharacteristic>();
        for(var c:compressorCurves){require(curves.put(key(c.id(),c.revisionId()),c)==null,"Duplicate curve revision.");
            binding(c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),fluidSourceId,fluidRevisionId,fluidSha256);
            require(!c.recordedAt().isAfter(recordedAt),"Curve recorded after aggregate.");synthetic(c.origin(),origin);}
        for(var c:valveCharacteristics){require(valves.put(key(c.id(),c.revisionId()),c)==null,"Duplicate valve revision.");
            binding(c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),fluidSourceId,fluidRevisionId,fluidSha256);
            require(!c.recordedAt().isAfter(recordedAt),"Valve recorded after aggregate.");synthetic(c.origin(),origin);}
        var regulators=new HashMap<String,SimulationRegulatorCharacteristicRevision>();
        for(var c:regulatorCharacteristics){require(regulators.put(key(c.id(),c.revisionId()),c)==null,"Duplicate regulator revision.");
            binding(c.fluidSourceId(),c.fluidRevisionId(),c.fluidSha256(),fluidSourceId,fluidRevisionId,fluidSha256);
            require(origin==Origin.SYNTHETIC && !c.recordedAt().isAfter(recordedAt),"Synthetic regulator provenance required.");}
        var ids=new HashSet<String>();var usedCurves=new HashSet<String>();var usedValves=new HashSet<String>();var usedRegulators=new HashSet<String>();
        var limits=new HashMap<String,EnumMap<Quantity,GovernedLimit>>();
        for(var l:governedLimits){var m=limits.computeIfAbsent(l.equipmentId(),unused->new EnumMap<>(Quantity.class));
            require(m.put(l.quantity(),l)==null,"Duplicate equipment limit.");}
        for(var e:equipment){require(ids.add(e.id()),"Duplicate equipment.");
            if(e.kind()==Kind.COMPRESSOR){var k=key(e.curveId(),e.curveRevisionId());var c=curves.get(k);require(c!=null,"Missing exact compressor curve.");usedCurves.add(k);
                inside(e.configuredSpeedRevolutionsPerMinute(),c.speedLines().getFirst().rotationalSpeedRevolutionsPerMinute(),c.speedLines().getLast().rotationalSpeedRevolutionsPerMinute());}
            else if(e.kind()==Kind.VALVE) {var k=key(e.characteristicId(),e.characteristicRevisionId());var c=valves.get(k);require(c!=null,"Missing exact valve characteristic.");usedValves.add(k);
                inside(e.configuredOpeningFraction(),c.openingLines().getFirst().openingFraction(),c.openingLines().getLast().openingFraction());}
            else {var config=e.regulator();var k=key(config.characteristicId(),config.characteristicRevisionId());var c=regulators.get(k);
                require(c!=null,"Missing exact regulator characteristic.");usedRegulators.add(k);
                inside(config.downstreamSetpointPascalsAbsolute(),c.minimumDownstreamPressurePascalsAbsolute(),c.maximumDownstreamPressurePascalsAbsolute());}
            var m=limits.get(e.id());var expected=EnumSet.of(Quantity.INLET_PRESSURE_PASCALS_ABSOLUTE,Quantity.INLET_TEMPERATURE_KELVIN,Quantity.MASS_FLOW_KILOGRAMS_PER_SECOND,
                    e.kind()==Kind.COMPRESSOR?Quantity.ROTATIONAL_SPEED_REVOLUTIONS_PER_MINUTE:e.kind()==Kind.VALVE?Quantity.OPENING_FRACTION:Quantity.DOWNSTREAM_SETPOINT_PRESSURE_PASCALS_ABSOLUTE);
            require(m!=null && m.keySet().equals(expected),"Complete kind-specific limits required.");
            var q=e.kind()==Kind.COMPRESSOR?Quantity.ROTATIONAL_SPEED_REVOLUTIONS_PER_MINUTE:e.kind()==Kind.VALVE?Quantity.OPENING_FRACTION:Quantity.DOWNSTREAM_SETPOINT_PRESSURE_PASCALS_ABSOLUTE;
            var l=m.get(q);inside(e.kind()==Kind.COMPRESSOR?e.configuredSpeedRevolutionsPerMinute():e.kind()==Kind.VALVE?e.configuredOpeningFraction():e.regulator().downstreamSetpointPascalsAbsolute(),l.minimumInclusive(),l.maximumInclusive());
            if(e.kind()==Kind.REGULATOR){var c=regulators.get(key(e.regulator().characteristicId(),e.regulator().characteristicRevisionId()));
                var inlet=m.get(Quantity.INLET_PRESSURE_PASCALS_ABSOLUTE);var temperature=m.get(Quantity.INLET_TEMPERATURE_KELVIN);
                inside(inlet.minimumInclusive(),c.minimumUpstreamPressurePascalsAbsolute(),c.maximumUpstreamPressurePascalsAbsolute());
                inside(inlet.maximumInclusive(),c.minimumUpstreamPressurePascalsAbsolute(),c.maximumUpstreamPressurePascalsAbsolute());
                inside(l.minimumInclusive(),c.minimumDownstreamPressurePascalsAbsolute(),c.maximumDownstreamPressurePascalsAbsolute());
                inside(l.maximumInclusive(),c.minimumDownstreamPressurePascalsAbsolute(),c.maximumDownstreamPressurePascalsAbsolute());
                inside(c.referenceTemperatureKelvin(),temperature.minimumInclusive(),temperature.maximumInclusive());}

        }
        require(usedRegulators.equals(regulators.keySet()) && usedCurves.equals(curves.keySet()) && usedValves.equals(valves.keySet()) && ids.equals(limits.keySet()),"Unused characteristic or limit.");
    }
    public boolean effectiveAt(Instant at){return valid(recordedAt,effectiveFrom,effectiveUntil,at)
        && compressorCurves.stream().allMatch(c->c.effectiveAt(at)) && valveCharacteristics.stream().allMatch(c->c.effectiveAt(at)) && regulatorCharacteristics.stream().allMatch(c->c.effectiveAt(at));}
    public record Equipment(String id,String fromNodeId,String toNodeId,Kind kind,String curveId,String curveRevisionId,
            BigDecimal configuredSpeedRevolutionsPerMinute,String characteristicId,String characteristicRevisionId,BigDecimal configuredOpeningFraction,RegulatorConfiguration regulator){
        public Equipment(String id,String fromNodeId,String toNodeId,Kind kind,String curveId,String curveRevisionId,BigDecimal speed,String characteristicId,String characteristicRevisionId,BigDecimal opening){this(id,fromNodeId,toNodeId,kind,curveId,curveRevisionId,speed,characteristicId,characteristicRevisionId,opening,null);}
        public Equipment {id=text(id);fromNodeId=text(fromNodeId);toNodeId=text(toNodeId);require(!fromNodeId.equals(toNodeId) && kind!=null,"Distinct endpoints and kind required.");
            require((kind==Kind.REGULATOR)==(regulator!=null),"Kind-specific regulator configuration required.");
            if(kind==Kind.COMPRESSOR){curveId=text(curveId);curveRevisionId=text(curveRevisionId);positive(configuredSpeedRevolutionsPerMinute);
                require(characteristicId==null && characteristicRevisionId==null && configuredOpeningFraction==null,"Valve fields forbidden on compressor.");}
            else if(kind==Kind.VALVE) {characteristicId=text(characteristicId);characteristicRevisionId=text(characteristicRevisionId);fraction(configuredOpeningFraction,false);
                require(curveId==null && curveRevisionId==null && configuredSpeedRevolutionsPerMinute==null,"Compressor fields forbidden on valve.");}
            else {require(curveId==null && curveRevisionId==null && configuredSpeedRevolutionsPerMinute==null && characteristicId==null && characteristicRevisionId==null && configuredOpeningFraction==null,"Old equipment fields forbidden on regulator.");}}
    }
    public record CompressorCurve(String id,String revisionId,Instant recordedAt,Instant effectiveFrom,Instant effectiveUntil,Origin origin,String evidenceReference,
            String fluidSourceId,String fluidRevisionId,String fluidSha256,String headDefinitionReference,String efficiencyDefinitionReference,String interpolationMethodReference,
            BigDecimal referenceInletPressurePascalsAbsolute,BigDecimal referenceInletTemperatureKelvin,List<SpeedLine> speedLines){
        public CompressorCurve {id=text(id);revisionId=text(revisionId);interval(recordedAt,effectiveFrom,effectiveUntil);require(origin!=null,"Curve origin required.");evidenceReference=text(evidenceReference);
            fluidSourceId=text(fluidSourceId);fluidRevisionId=text(fluidRevisionId);fluidSha256=digest(fluidSha256);headDefinitionReference=text(headDefinitionReference);
            efficiencyDefinitionReference=text(efficiencyDefinitionReference);interpolationMethodReference=text(interpolationMethodReference);
            positive(referenceInletPressurePascalsAbsolute);positive(referenceInletTemperatureKelvin);speedLines=copy(speedLines);require(!speedLines.isEmpty(),"Supplied speed lines required.");
            BigDecimal prior=null;for(var line:speedLines){ascending(prior,line.rotationalSpeedRevolutionsPerMinute());prior=line.rotationalSpeedRevolutionsPerMinute();}}
        public boolean effectiveAt(Instant at){return valid(recordedAt,effectiveFrom,effectiveUntil,at);}
    }
    public record SpeedLine(BigDecimal rotationalSpeedRevolutionsPerMinute,List<CompressorPoint> points){
        public SpeedLine {positive(rotationalSpeedRevolutionsPerMinute);points=copy(points);require(points.size()>=2,"At least two compressor points required.");
            BigDecimal prior=null;for(var p:points){ascending(prior,p.massFlowKilogramsPerSecond());prior=p.massFlowKilogramsPerSecond();}}
    }
    public record CompressorPoint(BigDecimal massFlowKilogramsPerSecond,BigDecimal specificHeadJoulesPerKilogram,BigDecimal efficiencyFraction){
        public CompressorPoint {positive(massFlowKilogramsPerSecond);nonnegative(specificHeadJoulesPerKilogram);fraction(efficiencyFraction,true);}}
    public record ValveCharacteristic(String id,String revisionId,Instant recordedAt,Instant effectiveFrom,Instant effectiveUntil,Origin origin,String evidenceReference,
            String fluidSourceId,String fluidRevisionId,String fluidSha256,BigDecimal referenceTemperatureKelvin,List<OpeningLine> openingLines){
        public ValveCharacteristic {id=text(id);revisionId=text(revisionId);interval(recordedAt,effectiveFrom,effectiveUntil);require(origin!=null,"Valve origin required.");evidenceReference=text(evidenceReference);
            fluidSourceId=text(fluidSourceId);fluidRevisionId=text(fluidRevisionId);fluidSha256=digest(fluidSha256);positive(referenceTemperatureKelvin);
            openingLines=copy(openingLines);require(!openingLines.isEmpty(),"Supplied opening lines required.");BigDecimal prior=null;
            for(var l:openingLines){ascending(prior,l.openingFraction());prior=l.openingFraction();}}
        public boolean effectiveAt(Instant at){return valid(recordedAt,effectiveFrom,effectiveUntil,at);}
    }
    public record OpeningLine(BigDecimal openingFraction,List<ValvePoint> points){
        public OpeningLine {fraction(openingFraction,false);points=copy(points);require(points.size()>=2,"At least two valve points required.");
            BigDecimal prior=null;for(var p:points){ascending(prior,p.differentialPressurePascals());prior=p.differentialPressurePascals();}}
    }
    public record ValvePoint(BigDecimal differentialPressurePascals,BigDecimal massFlowKilogramsPerSecond){
        public ValvePoint {nonnegative(differentialPressurePascals);nonnegative(massFlowKilogramsPerSecond);}}
    public record GovernedLimit(String equipmentId,Quantity quantity,BigDecimal minimumInclusive,BigDecimal maximumInclusive,String evidenceReference){
        public GovernedLimit {equipmentId=text(equipmentId);evidenceReference=text(evidenceReference);require(quantity!=null,"Limit quantity required.");
            nonnegative(minimumInclusive);nonnegative(maximumInclusive);require(minimumInclusive.compareTo(maximumInclusive)<=0,"Ordered limit range required.");
            if(quantity==Quantity.INLET_PRESSURE_PASCALS_ABSOLUTE || quantity==Quantity.INLET_TEMPERATURE_KELVIN || quantity==Quantity.DOWNSTREAM_SETPOINT_PRESSURE_PASCALS_ABSOLUTE)positive(minimumInclusive);
            if(quantity==Quantity.OPENING_FRACTION){fraction(minimumInclusive,false);fraction(maximumInclusive,false);}}}
    public record GovernanceBinding(String definitionId,int definitionVersion,String targetTypeId,String purposeId){
        public GovernanceBinding {definitionId=text(definitionId);targetTypeId=text(targetTypeId);purposeId=text(purposeId);require(definitionVersion>0,"Positive definition version required.");}}
    private static void binding(String s,String r,String h,String es,String er,String eh){require(s.equals(es)&&r.equals(er)&&h.equals(eh),"Exact fluid binding required.");}
    private static String key(String id,String rev){return id.length()+":"+id+rev;}
    private static void synthetic(Origin child,Origin parent){require(child!=Origin.SYNTHETIC||parent==Origin.SYNTHETIC,"Synthetic characteristic cannot be promoted.");}
    private static boolean valid(Instant recorded,Instant from,Instant until,Instant at){return at!=null&&!at.isBefore(recorded)&&!at.isBefore(from)&&(until==null||at.isBefore(until));}
    private static void interval(Instant r,Instant f,Instant u){require(r!=null&&f!=null&&(u==null||u.isAfter(f)),"Coherent supplied time interval required.");}
    private static String text(String v){require(v!=null&&!v.isBlank()&&!v.trim().isEmpty(),"Nonblank supplied text required.");return v.trim();}
    private static String digest(String v){require(v!=null&&v.matches("[0-9a-f]{64}"),"Exact digest required.");return v;}
    private static <T> List<T> copy(List<T> v){require(v!=null&&v.stream().noneMatch(Objects::isNull),"Nonnull immutable children required.");return List.copyOf(v);}
    private static void ascending(BigDecimal p,BigDecimal v){require(p==null||p.compareTo(v)<0,"Strict ascending unique coordinates required.");}
    private static void positive(BigDecimal v){require(v!=null&&v.signum()>0,"Positive supplied SI value required.");}
    private static void nonnegative(BigDecimal v){require(v!=null&&v.signum()>=0,"Nonnegative supplied SI value required.");}
    private static void fraction(BigDecimal v,boolean positive){nonnegative(v);require((!positive||v.signum()>0)&&v.compareTo(BigDecimal.ONE)<=0,"Fraction outside supplied range.");}
    private static void inside(BigDecimal v,BigDecimal lo,BigDecimal hi){require(v.compareTo(lo)>=0&&v.compareTo(hi)<=0,"Configured value outside supplied range.");}
    private static void require(boolean ok,String m){if(!ok)throw new InvalidSimulationValueException(m);}
}
