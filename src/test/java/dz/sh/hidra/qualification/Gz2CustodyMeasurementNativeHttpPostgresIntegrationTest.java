/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : Gz2CustodyMeasurementNativeHttpPostgresIntegrationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
 * @Type        : Class
 * @Layer       : Test
 * @Module      : qualification
 * @Package     : dz.sh.hidra.qualification
 * @Description : Qualifies T20 via signed HTTP, real owner publications and PostgreSQL immutable evidence.
 */
package dz.sh.hidra.qualification;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.*;
class Gz2CustodyMeasurementNativeHttpPostgresIntegrationTest {
    private static final String PRODUCT="/api/v1/custody/engineering-products";
    private static final String POINT="/api/v1/telemetry/points/";
    private static final String VOLUME="gz2-custody-volume";
    private record Reading(String raw,String assessment,String trusted){}
    private static String id(){return Gz2SignedWorkflowFixture.id();}
    private static String field(JsonNode node,String name){
        assertTrue(node.hasNonNull(name),"Missing signed source field: "+name);
        return node.get(name).asText();
    }
    private static String query(String path,String... pairs){
        return Gz2SignedWorkflowFixture.query(path,pairs);
    }
    @Test void capturesQualifiedObservedIntervalAndPreservesScopedReceipt() throws Exception {
        try(var postgres=new PostgreSQLContainer<>("postgres:16").withDatabaseName("hidra_gz2_t20")){
            postgres.start();
            var f=new Gz2SignedWorkflowFixture(postgres);
            f.sql.update("""
                INSERT INTO hidra_telemetry_unit(id,code,symbol,dimension,active,system_defined,created_at,updated_at)
                VALUES (?,'T20_SYNTHETIC_VOLUME','m3','VOLUME',true,false,now(),now())
                """,VOLUME);
            // HRA-111 preserves the Custody-owned observed-unit FK; this synthetic
            // mirror does not replace Telemetry's active VOLUME dimension check.
            f.sql.update("""
                INSERT INTO public.hidra_custody_catalog_entry
                  (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
                VALUES (?,'MEASUREMENT_UNIT','T20_SYNTHETIC_VOLUME',true,0,false,now(),now())
                """,VOLUME);
            grantProduct(f);
            String transfer=id(),agreement=id(),period=id(),run=id(),meter=id(),partyRole=id(),agreementParty=id();
            setupCustody(f,transfer,agreement,period,run,meter,partyRole,agreementParty);
            String companyGrant="custody-company:"+f.company;
            String pointGrant="custody-transfer-point:"+transfer;
            f.paired(f.author,"custody:measurements","MEASUREMENT",companyGrant,pointGrant,"capture","read");
            f.paired(f.reader,"custody:measurements","MEASUREMENT",companyGrant,pointGrant,"read");
            f.paired(f.revoked,"custody:measurements","MEASUREMENT",companyGrant,pointGrant,"read");
            f.paired(f.foreign,"custody:measurements","MEASUREMENT",
                    "custody-company:"+f.foreignCompany,pointGrant,"capture","read");
            f.paired(f.wrongAsset,"custody:measurements","MEASUREMENT",
                    companyGrant,"custody-transfer-point:"+f.secondAsset,"capture","read");
            f.permit(f.producer,"telemetry:commission:manage","TELEMETRY_COMMISSIONING",
                    "CUSTOM","telemetry-company:"+f.company);
            f.global(f.producer,"telemetry:points:execute","telemetry:points:read");
            Instant observed=Instant.now().minusSeconds(20).truncatedTo(ChronoUnit.SECONDS);
            try(var runtime=f.start()){
                String author=runtime.login(f.author),reviewer=runtime.login(f.reviewer);
                String producer=runtime.login(f.producer),reader=runtime.login(f.reader);
                String foreign=runtime.login(f.foreign),wrong=runtime.login(f.wrongAsset);
                String revoked=runtime.login(f.revoked);
                JsonNode pin=publishProduct(f,runtime,author,reviewer);
                String publication=field(pin,"revisionId");
                JsonNode commissioned=commission(f,runtime,producer,observed.minusSeconds(600));
                String quality=prepareTelemetry(f,commissioned);
                Reading reading=trust(f,runtime,producer,commissioned,quality,observed);
                String evidence=id();
                f.sql.update("""
                    INSERT INTO public.hidra_telemetry_custody_interval_evidence
                    (id,trusted_reading_id,meter_run_snapshot_id,metering_system_id,
                     asset_type,asset_id,measurement_kind,observed_quantity,unit_id,
                     interval_start,interval_end)
                    VALUES (?,?,?,?,'FACILITY',?,'INTERVAL_VOLUME_TOTAL',75.500000,?,?,?)
                    """,evidence,reading.trusted(),run,meter,f.asset,VOLUME,
                    java.sql.Timestamp.from(observed.minusSeconds(60)),
                    java.sql.Timestamp.from(observed));
                String route="/api/v1/custody/measurement-periods/"+period+"/measurements";
                assertEquals(0,runtime.get(route,reader).path("total").asInt(-1));
                assertEquals(0,runtime.get(route,reader).path("items").size());
                runtime.json("GET",route,foreign,null,404);
                runtime.json("GET",route,wrong,null,404);
                assertEquals(401,runtime.send("GET",route,null,null).statusCode());
                Map<String,Object> cmd=Map.of(
                    "commandId",id(),"meterRunSnapshotId",run,"measurementTypeId",
                    f.catalog("custody","MEASUREMENT_TYPE","T20_VOLUME_TOTAL"),
                    "productPublicationId",publication,"agreementPartyId",agreementParty,
                    "unitId",VOLUME,"intervalEvidenceId",evidence);
                assertEquals(404,runtime.send("POST",route,foreign,cmd).statusCode());
                assertEquals(404,runtime.send("POST",route,wrong,cmd).statusCode());
                assertEquals(401,runtime.send("POST",route,null,cmd).statusCode());
                assertEquals(0,f.sql.queryForObject(
                    "SELECT count(*) FROM hidra_custody_measurement_receipt",Integer.class));
                var mismatched=new java.util.HashMap<>(cmd);mismatched.put("unitId",f.pressureUnit);
                assertEquals(400,runtime.send("POST",route,author,mismatched).statusCode());
                var badMeter=new java.util.HashMap<>(cmd);badMeter.put("meterRunSnapshotId",id());
                assertEquals(400,runtime.send("POST",route,author,badMeter).statusCode());
                var badParty=new java.util.HashMap<>(cmd);badParty.put("agreementPartyId",id());
                assertEquals(400,runtime.send("POST",route,author,badParty).statusCode());
                var badProduct=new java.util.HashMap<>(cmd);badProduct.put("productPublicationId",id());
                assertEquals(400,runtime.send("POST",route,author,badProduct).statusCode());
                var missing=new java.util.HashMap<>(cmd);missing.put("intervalEvidenceId",id());
                assertEquals(400,runtime.send("POST",route,author,missing).statusCode());
                String wrongBinding=id(),outside=id();
                f.sql.update("""
                    INSERT INTO public.hidra_telemetry_custody_interval_evidence
                    (id,trusted_reading_id,meter_run_snapshot_id,metering_system_id,
                     asset_type,asset_id,measurement_kind,observed_quantity,unit_id,interval_start,interval_end)
                    VALUES (?,?,?,?,'FACILITY',?,'INTERVAL_VOLUME_TOTAL',75.500000,?,?,?)
                    """,wrongBinding,reading.trusted(),run,meter,f.secondAsset,VOLUME,
                    java.sql.Timestamp.from(observed.minusSeconds(60)),java.sql.Timestamp.from(observed));
                f.sql.update("""
                    INSERT INTO public.hidra_telemetry_custody_interval_evidence
                    (id,trusted_reading_id,meter_run_snapshot_id,metering_system_id,
                     asset_type,asset_id,measurement_kind,observed_quantity,unit_id,interval_start,interval_end)
                    VALUES (?,?,?,?,'FACILITY',?,'INTERVAL_VOLUME_TOTAL',75.500000,?,?,?)
                    """,outside,reading.trusted(),run,meter,f.asset,VOLUME,
                    java.sql.Timestamp.from(observed.minusSeconds(10800)),java.sql.Timestamp.from(observed));
                var badBinding=new java.util.HashMap<>(cmd);badBinding.put("intervalEvidenceId",wrongBinding);
                assertEquals(400,runtime.send("POST",route,author,badBinding).statusCode());
                var badWindow=new java.util.HashMap<>(cmd);badWindow.put("intervalEvidenceId",outside);
                assertEquals(400,runtime.send("POST",route,author,badWindow).statusCode());
                assertEquals(0,f.sql.queryForObject("SELECT count(*) FROM hidra_custody_measurement_snapshot",
                    Integer.class));
                JsonNode created=runtime.json("POST",route,author,cmd,201);
                String measurement=field(created,"measurementId");
                assertEquals(f.company,field(created,"companyId"));
                assertEquals(transfer,field(created,"transferPointId"));
                assertEquals("OBSERVED",field(created,"quantityBasis"));
                assertEquals("75.500000",created.path("observedValue").decimalValue().setScale(6).toPlainString());
                assertEquals(reading.trusted(),field(created,"trustedReadingId"));
                assertEquals("false",created.path("acceptedForCustody").asText());
                assertFalse(created.path("replay").asBoolean());
                String getRoute=route+"/"+measurement;
                assertEquals(created,runtime.get(getRoute,reader));
                var page=runtime.get(route+"?page=0&size=1",reader);
                assertEquals(1,page.path("total").asInt(-1));
                assertEquals(1,page.path("totalPages").asInt(-1));
                assertEquals(created,page.path("items").get(0));
                assertEquals(50,runtime.get(route,reader).path("size").asInt(-1));
                assertEquals(1,runtime.get(route+"?page=1&size=1",reader).path("total").asInt(-1));
                assertEquals(0,runtime.get(route+"?page=1&size=1",reader).path("items").size());
                assertTrue(runtime.send("GET",route,reader,null).headers().firstValue("Cache-Control").orElse("").contains("no-store"));
                runtime.json("GET",route+"?size=201",reader,null,400);
                runtime.json("GET",route+"?page=-1",reader,null,400);
                runtime.json("GET",route+"?size=0",reader,null,400);
                var denied=runtime.send("GET",route+"?size=0",foreign,null);
                assertEquals(404,denied.statusCode());
                assertFalse(denied.body().contains(measurement));
                assertFalse(denied.body().contains("\"total\""));
                runtime.json("GET","/api/v1/custody/measurement-periods/"+id()+"/measurements",reader,null,404);
                assertEquals(1,f.sql.queryForObject("SELECT count(*) FROM hidra_custody_measurement_receipt",Integer.class));
                assertEquals(404,runtime.send("GET",getRoute,foreign,null).statusCode());
                assertEquals(404,runtime.send("GET",getRoute,wrong,null).statusCode());
                // A new command conflicts with the already captured eligible source.
                var doubleSource=new java.util.HashMap<>(cmd);doubleSource.put("commandId",id());
                assertEquals(409,runtime.send("POST",route,author,doubleSource).statusCode());
                var changed=new java.util.HashMap<>(cmd);changed.put("intervalEvidenceId",id());
                assertEquals(409,runtime.send("POST",route,author,changed).statusCode());
                // An exact retry alone is valid after current source eligibility changes.
                f.sql.update("UPDATE hidra_custody_metering_system SET active=false WHERE id=?",meter);
                JsonNode replay=runtime.json("POST",route,author,cmd,200);
                assertTrue(replay.path("replay").asBoolean());
                assertEquals(measurement,field(replay,"measurementId"));
                assertEquals(1,f.sql.queryForObject(
                    "SELECT count(*) FROM hidra_custody_measurement_receipt",Integer.class));
                assertEquals(1,f.sql.queryForObject(
                    "SELECT count(*) FROM hidra_custody_measurement_snapshot WHERE accepted_for_custody=false",
                    Integer.class));
                assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.update(
                    "UPDATE public.hidra_custody_measurement_receipt SET actor_id=? WHERE measurement_id=?",
                    id(),measurement));
                assertThrows(org.springframework.dao.DataAccessException.class,()->f.sql.update(
                    "DELETE FROM public.hidra_telemetry_custody_interval_evidence WHERE id=?",evidence));
                f.sql.update("""
                    UPDATE hidra_identity_user_permission_grant SET status='REVOKED',revoked_at=now()
                     WHERE user_id=? AND scope_type='CUSTOM'
                       AND scope_reference_id IN (?,?)
                    """,f.revoked,companyGrant,pointGrant);
                assertEquals(404,runtime.send("GET",getRoute,revoked,null).statusCode());
                runtime.json("GET",route,revoked,null,404);
                assertEquals(200,runtime.send("GET",getRoute,reader,null).statusCode());
            }
        }
    }
    private static void grantProduct(Gz2SignedWorkflowFixture f){
        f.paired(f.author,"custody:engineering-product-publication","PRODUCT_ENGINEERING_PUBLICATION",
            "custody-company:"+f.company,"custody-product:"+f.product,"read","prepare","publish");
        f.paired(f.reviewer,"custody:engineering-product-publication","PRODUCT_ENGINEERING_PUBLICATION",
            "custody-company:"+f.company,"custody-product:"+f.product,"read");
        f.paired(f.author,"custody:engineering-product","PRODUCT_ENGINEERING_DRAFT",
            "custody-company:"+f.company,"custody-product:"+f.product,"create","read","update");
        f.paired(f.author,"custody:engineering-product-properties","PRODUCT_ENGINEERING_PROPERTIES",
            "custody-company:"+f.company,"custody-product:"+f.product,"write","read");
        f.global(f.author,"workflow:instances:execute","workflow:tasks:execute");
        f.global(f.reviewer,"workflow:tasks:execute","workflow:tasks:read");
    }
    private static void setupCustody(Gz2SignedWorkflowFixture f,String transfer,String agreement,String period,
            String run,String meter,String role,String agreementParty){
        var sql=f.sql;
        String type=f.catalog("custody","AGREEMENT_TYPE","T20_SYNTHETIC_GAS");
        sql.update("""
            INSERT INTO public.hidra_party_role(id,code,qualification_required_by_default,status,created_at,updated_at)
            VALUES (?,'SHIPPER',false,'ACTIVE',now(),now())
            """,role);
        sql.update("""
            INSERT INTO public.hidra_custody_transfer_point
            (id,code,name_fr,topology_asset_type_code,topology_asset_id,direction,
             product_type_id,status,effective_from,created_at,updated_at)
            VALUES (?,?,'Synthetic GZ2 transfer','FACILITY',?,'OUTBOUND',?,'ACTIVE',now()-interval '1 hour',now(),now())
            """,transfer,transfer,f.asset,f.product);
        sql.update("""
            INSERT INTO public.hidra_custody_agreement
            (id,agreement_number,agreement_type_id,title,transfer_point_id,status,valid_from,created_at,updated_at)
            VALUES (?,?,?,'Synthetic gas transfer',?,'ACTIVE',now()-interval '1 hour',now(),now())
            """,agreement,agreement,type,transfer);
        sql.update("""
            INSERT INTO public.hidra_custody_agreement_party
            (id,agreement_id,party_role_id,party_id,party_name_snapshot,effective_from,created_at)
            VALUES (?,?,?,?,'Synthetic GZ2 party',now()-interval '1 hour',now())
            """,agreementParty,agreement,role,f.company);
        sql.update("""
            INSERT INTO public.hidra_custody_measurement_period
            (id,period_code,agreement_id,transfer_point_id,period_start,period_end,status,created_at,updated_at)
            VALUES (?,?,?,?,now()-interval '20 minutes',now()+interval '2 hours','OPEN',now(),now())
            """,period,period,agreement,transfer);
        sql.update("""
            INSERT INTO public.hidra_custody_metering_system
            (id,metering_system_code,name,transfer_point_id,topology_asset_type_code,topology_asset_id,
             active,effective_from,created_at,updated_at)
            VALUES (?,?,'Synthetic GZ2 meter',?,'FACILITY',?,true,now()-interval '1 hour',now(),now())
            """,meter,meter,transfer,f.asset);
        sql.update("""
            INSERT INTO public.hidra_custody_meter_run_snapshot
            (id,measurement_period_id,metering_system_id,meter_run_code_snapshot,snapshot_at,created_at)
            VALUES (?,?,?,?,now(),now())
            """,run,period,meter,"T20-RUN");
    }
    private static JsonNode publishProduct(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer) throws Exception {
        JsonNode created = r.json("POST", PRODUCT, author, Map.of("companyPartyId", f.company,
                "catalogProductId", f.product, "localCode", "GZ2-GAS", "creationRequestId", id(),
                "name", "Synthetic GZ2 gas", "evidenceReference", "gz2-synthetic-product", "classification", "GAS"), 201);
        String path = PRODUCT + "/" + field(created, "draftId");
        var source = Map.of("origin", "SYNTHETIC", "reference", "GZ2-SYNTHETIC", "evidenceReference", "gz2-synthetic-gas");
        var method = Map.of("reference", "gz2-synthetic-method", "revision", "v1");
        var pressure = Map.of("minimum", "50", "maximum", "150", "unitId", f.pressureUnit);
        var temperature = Map.of("minimum", "250", "maximum", "350", "unitId", "gz2-temperature");
        var facts = Map.of("requestId", id(), "expectedVersion", 0, "expectedProductMetadataVersion", 1,
                "source", source, "composition", Map.of("basis", "MOLE_FRACTION",
                        "components", List.of(Map.of("reference", "GAS-A", "fraction", "1.0000")), "method", method, "source", source),
                "methods", List.of(Map.of("key", method, "origin", "SYNTHETIC", "basis", "MOLE_FRACTION",
                        "components", List.of("GAS-A"), "properties", List.of("DENSITY"),
                        "absolutePressure", pressure, "absoluteTemperature", temperature, "evidenceReference", "gz2-method")),
                "properties", List.of(Map.of("observationId", "density", "property", "DENSITY", "value", "1.200",
                        "unitId", "gz2-density", "absolutePressure", Map.of("value", "100", "unitId", f.pressureUnit),
                        "absoluteTemperature", Map.of("value", "300", "unitId", "gz2-temperature"), "method", method, "source", source)));
        r.json("PUT", query(path + "/properties", "companyPartyId", f.company), author, facts, 201);
        var review = f.review("custody", "PRODUCT_SOURCE_PUBLICATION", "PRODUCT_SOURCE_PUBLICATION_APPROVAL", false);
        f.sql.update("""
                INSERT INTO hidra_workflow_custody_publication_policy
                (company_party_id,definition_id,definition_version,target_type_id,purpose_id,active,created_at,updated_at)
                VALUES (?,?,1,?,?,true,now(),now())
                """, f.company, review.definition(), review.type(), review.purpose());
        String publications = path + "/publications";
        String candidate = field(r.json("POST", query(publications + "/candidates", "companyPartyId", f.company),
                author, Map.of("requestId", id(), "metadataVersion", 1, "suppliedVersion", 1), 201), "candidateDigest");
        var decision = workflowDecision(f, r, author, reviewer, review, "custody", candidate, candidate, id());
        JsonNode publication = r.json("POST", query(publications, "companyPartyId", f.company), author,
                Map.of("requestId", id(), "candidateDigest", candidate, "expectedEpoch", 0, "approval", decision), 201);
        assertEquals(0, publication.path("publication").path("envelope").path("diagnostics").size());
        JsonNode current = r.get(query(publications + "/current", "companyPartyId", f.company), author);
        assertEquals(publication.path("baselinePin"), current.path("baselinePin"));
        return current.path("baselinePin");
    }

    private static Map<String, String> workflowDecision(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r,
            String author, String reviewer, Gz2SignedWorkflowFixture.Review review, String module,
            String target, String locator, String correlation) throws Exception {
        String instance = field(r.json("POST", "/api/v1/workflow/instances", author, Map.of(
                "definitionId", review.definition(), "definitionVersion", 1, "workflowPurposeId", review.purpose(),
                "targetModule", module, "targetTypeId", review.type(), "targetId", target, "targetCodeSnapshot", locator,
                "currentStepId", review.step(), "startedByActorId", f.author, "correlationId", correlation), 200), "id");
        String task = field(r.json("POST", "/api/v1/workflow/tasks", author, Map.of("instanceId", instance,
                "stepId", review.step(), "assignedActorId", f.reviewer, "taskLabelSnapshot", "Synthetic review"), 200), "id");
        String taskPath = "/api/v1/workflow/tasks/" + task;
        JsonNode current = r.get(taskPath, reviewer);
        JsonNode result = r.json("POST", taskPath + "/transitions/" + review.transition() + "/execute", reviewer,
                Map.of("expectedTaskUpdatedAt", field(current, "updatedAt"), "correlationId", correlation), 200);
        assertEquals("APPROVE", field(result, "decision"));
        assertEquals("COMPLETED", field(result, "instanceStatus"));
        return Map.of("instanceId", instance, "taskId", task, "actionId", field(result, "actionId"));
    }

    private static JsonNode commission(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String producer,
            Instant start) throws Exception {
        JsonNode receipt = r.json("POST", "/api/v1/telemetry/commissioning", producer, Map.ofEntries(
                Map.entry("requestId", id()), Map.entry("companyPartyId", f.company), Map.entry("assetType", "FACILITY"),
                Map.entry("assetId", f.asset), Map.entry("sourceCode", "GZ2-S"), Map.entry("sourceNameFr", "Source synthétique"),
                Map.entry("sourceTypeId", f.telemetryType("SOURCE_TYPE", null)), Map.entry("protocolId", f.telemetryType("PROTOCOL", null)),
                Map.entry("deviceCode", "GZ2-D"), Map.entry("deviceNameFr", "Appareil synthétique"),
                Map.entry("deviceTypeId", f.telemetryType("DEVICE_TYPE", null)), Map.entry("pointCode", "GZ2-V"),
                Map.entry("pointNameFr", "Volume synthétique"), Map.entry("pointTypeId", f.telemetryType("POINT_TYPE", null)),
                Map.entry("signalTypeId", f.telemetryType("SIGNAL_TYPE", "NUMERIC")), Map.entry("unitId", VOLUME),
                Map.entry("bindingRoleId", f.telemetryType("BINDING_ROLE", null)), Map.entry("externalTagName", "GZ2.VOLUME"),
                Map.entry("validFrom", start.toString())), 200);
        assertEquals(f.asset, field(receipt, "assetId"));
        assertFalse(field(receipt, "bindingId").isBlank());
        return receipt;
    }

    private static String prepareTelemetry(Gz2SignedWorkflowFixture f, JsonNode commissioned) {
        String point = field(commissioned, "pointId");
        String reference = "telemetry-source-point:" + field(commissioned, "sourceId") + ":" + point;
        f.paired(f.producer, "telemetry:raw-reading", "TELEMETRY_READING", "telemetry-company:" + f.company,
                reference, "ingest", "read");
        f.paired(f.producer, "telemetry:trusted-reading", "TRUSTED_READING", "telemetry-company:" + f.company,
                reference, "qualify", "read");
        f.paired(f.reader, "telemetry:raw-reading", "TELEMETRY_READING", "telemetry-company:" + f.company, reference, "read");
        f.paired(f.reader, "telemetry:trusted-reading", "TRUSTED_READING", "telemetry-company:" + f.company, reference, "read");
        f.global(f.reader, "telemetry:points:read");
        String quality = f.telemetryType("QUALITY_CODE", null);
        f.sql.update("""
                INSERT INTO hidra_telemetry_validation_rule(id,code,name,scope_type,scope_reference_id,rule_type_id,
                    severity,action_on_failure,configuration_json,active,valid_from,created_at,updated_at)
                VALUES (?,'T09_RAW_QUALITY','Synthetic GZ2 trust','POINT',?,?,'ERROR','REJECT',
                    cast(? AS jsonb),true,now()-interval '1 hour',now(),now())
                """, id(), point, f.telemetryType("VALIDATION_RULE_TYPE", null),
                "{\"qualityCodeId\":\"" + quality + "\",\"trustLevel\":\"HIGH\",\"maxPastAgeSeconds\":3600,\"maxFutureSkewSeconds\":120}");
        return quality;
    }

    private static Reading trust(Gz2SignedWorkflowFixture f, Gz2SignedWorkflowFixture.Runtime r, String producer,
            JsonNode commissioned, String quality, Instant observed) throws Exception {
        String path = POINT + field(commissioned, "pointId") + "/readings";
        var raw = Map.of("sourceId", field(commissioned, "sourceId"), "sourceSequenceNumber", id(), "numericValue", 75.5,
                "qualityCodeId", quality, "unitId", VOLUME, "sourceTimestamp", observed.toString());
        String rawId = field(r.json("POST", path, producer, raw, 201), "id");
        JsonNode replay = r.json("POST", path, producer, raw, 200);
        assertEquals(rawId, field(replay, "id"));
        assertTrue(replay.path("replayed").asBoolean());
        var scope = Map.of("companyPartyId", f.company);
        String assessment = field(r.json("POST", path + "/" + rawId + "/assessment", producer, scope, 201), "id");
        JsonNode trusted = r.json("POST", path + "/" + rawId + "/trust", producer, scope, 201);
        assertEquals(rawId, field(trusted, "rawReadingId"));
        assertEquals(assessment, field(trusted, "assessmentId"));
        assertEquals(field(commissioned, "bindingId"), field(trusted, "bindingId"));
        assertEquals(VOLUME, field(trusted, "unitId"));
        return new Reading(rawId, assessment, field(trusted, "id"));
    }
}
