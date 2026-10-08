/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationReferenceValidation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.adapter
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.custody.application.contract.planning.PlanningProductReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningUnitReferenceContract;
import dz.sh.hidra.modules.party.application.contract.planning.PlanningPartyReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTargetTopologyReferenceContract;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class NominationReferenceValidation {
    private final PlanRevisionJpaRepository revisions;
    private final PlanScenarioJpaRepository scenarios;
    private final PlanningCatalogEntryJpaRepository catalogs;
    private final PlanningProductReferenceContract products;
    private final PlanningUnitReferenceContract units;
    private final PlanningPartyReferenceContract parties;
    private final PlanningTargetTopologyReferenceContract topology;
    public NominationReferenceValidation(PlanRevisionJpaRepository revisions,PlanScenarioJpaRepository scenarios,
            PlanningCatalogEntryJpaRepository catalogs,PlanningProductReferenceContract products,
            PlanningUnitReferenceContract units,PlanningPartyReferenceContract parties,PlanningTargetTopologyReferenceContract topology) {
        this.revisions=Objects.requireNonNull(revisions);this.scenarios=Objects.requireNonNull(scenarios);
        this.catalogs=Objects.requireNonNull(catalogs);this.products=Objects.requireNonNull(products);
        this.units=Objects.requireNonNull(units);this.parties=Objects.requireNonNull(parties);this.topology=Objects.requireNonNull(topology);
    }
    public Nomination validate(Nomination value,Nomination old) {
        var revision=revisions.findByIdForShare(value.revisionId()).orElseThrow(()->invalid("Unknown revision"));
        require(value.revisionId().equals(revision.id()),"Revision identity mismatch");
        if(value.scenarioId()!=null) {
            var scenario=scenarios.findByIdForShare(value.scenarioId()).orElseThrow(()->invalid("Unknown scenario"));
            require(value.scenarioId().equals(scenario.id()) && value.revisionId().equals(scenario.revisionId()),"Scenario revision mismatch");
        }
        var type=catalogs.findByIdForShare(value.nominationTypeId()).orElseThrow(()->invalid("Unknown nomination type"));
        require(value.nominationTypeId().equals(type.id()) && "NOMINATION_TYPE".equals(type.catalogName()),"Exact NOMINATION_TYPE required");
        require(old!=null && Objects.equals(old.nominationTypeId(),value.nominationTypeId()) || type.active(),"Active fresh nomination type required");
        boolean sameProduct=old!=null && Objects.equals(old.productTypeId(),value.productTypeId());
        if(!sameProduct) {
            var product=products.resolve(value.productTypeId()).orElseThrow(()->invalid("Approved product required"));
            require(value.productTypeId().equals(product.id()) && product.active(),"Eligible product identity required");
        }
        boolean sameQuantity=old!=null && Objects.equals(old.quantityUnitId(),value.quantityUnitId());
        boolean sameRate=old!=null && Objects.equals(old.rateUnitId(),value.rateUnitId());
        if(!sameQuantity || !sameRate) {
            var resolved=units.resolve(value.quantityUnitId(),value.rateUnitId()).orElseThrow(()->invalid("Approved units and compatibility required"));
            require(resolved.quantity()!=null && value.quantityUnitId().equals(resolved.quantity().id()),"Quantity unit identity mismatch");
            require(sameQuantity || resolved.quantity().active(),"Active fresh quantity unit required");
            if(value.rateUnitId()==null) require(resolved.rate()==null,"Unexpected rate unit");
            else {
                require(resolved.rate()!=null && value.rateUnitId().equals(resolved.rate().id()),"Rate unit identity mismatch");
                require(sameRate || resolved.rate().active(),"Active fresh rate unit required");
                require(resolved.pairActive(),"Active fresh unit pair required");
            }
        }
        var freshParties=new TreeSet<String>();
        if(value.shipperPartyId()!=null && (old==null || !Objects.equals(old.shipperPartyId(),value.shipperPartyId()))) freshParties.add(value.shipperPartyId());
        if(value.counterpartyId()!=null && (old==null || !Objects.equals(old.counterpartyId(),value.counterpartyId()))) freshParties.add(value.counterpartyId());
        var resolvedParties=new HashMap<String,PlanningPartyReferenceContract.Party>();
        for(String id:freshParties) {
            var party=parties.resolve(id).orElseThrow(()->invalid("Unknown Party"));
            require(id.equals(party.id()),"Party identity mismatch");resolvedParties.put(id,party);
        }
        String shipperCode=null;
        if(value.shipperPartyId()!=null) {
            if(old!=null && Objects.equals(old.shipperPartyId(),value.shipperPartyId())) shipperCode=old.shipperPartyCodeSnapshot();
            else {shipperCode=resolvedParties.get(value.shipperPartyId()).code();require(nonblank(shipperCode),"Canonical shipper code required");}
        }
        String sourceCode=assetCode(value.sourceAssetType(),value.sourceAssetId(),old==null?null:old.sourceAssetType(),
                old==null?null:old.sourceAssetId(),old==null?null:old.sourceAssetCode());
        String destinationCode=assetCode(value.destinationAssetType(),value.destinationAssetId(),old==null?null:old.destinationAssetType(),
                old==null?null:old.destinationAssetId(),old==null?null:old.destinationAssetCode());
        return new Nomination(value.id(),value.revisionId(),value.scenarioId(),value.code(),value.nominationTypeId(),value.productTypeId(),
                value.quantity(),value.quantityUnitId(),value.rate(),value.rateUnitId(),value.sourceAssetType(),value.sourceAssetId(),sourceCode,
                value.destinationAssetType(),value.destinationAssetId(),destinationCode,value.shipperPartyId(),shipperCode,value.counterpartyId(),
                value.contractReferenceId(),value.priority(),value.status(),value.periodStart(),value.periodEnd(),value.createdAt(),value.updatedAt());
    }
    private String assetCode(String type,String id,String oldType,String oldId,String oldCode) {
        if(id==null) return null;
        if(Objects.equals(type,oldType) && Objects.equals(id,oldId)) return oldCode;
        var asset=topology.resolve(type,id).orElseThrow(()->invalid("Unknown typed topology asset"));
        require(id.equals(asset.id()) && nonblank(asset.code()),"Canonical topology identity/code required");return asset.code();
    }
    private static boolean nonblank(String v){return v!=null && !v.isBlank();}
    private static IllegalArgumentException invalid(String v){return new IllegalArgumentException(v);}
    private static void require(boolean condition,String message){if(!condition)throw invalid(message);}
}
