-- HAPI-778: bind every typed command selector/value to its physical lifecycle.
-- Additive: preserve all prior checks, routine bodies, rows and migration bytes.
-- These structural proofs do not establish native authorization or a final guard.
CREATE FUNCTION public.hidra_org_appointment_command_time_matches(v jsonb, actual jsonb)
RETURNS boolean LANGUAGE plpgsql IMMUTABLE PARALLEL SAFE
SET search_path=pg_catalog SET timezone='UTC' AS $command_time$
BEGIN
 IF v='null'::jsonb AND actual='null'::jsonb THEN RETURN true; END IF;
 IF pg_catalog.jsonb_typeof(v) IS DISTINCT FROM 'string'
    OR pg_catalog.jsonb_typeof(actual) IS DISTINCT FROM 'string'
    OR (v#>>'{}') !~ '^[+-]?[0-9]{4,6}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}([.][0-9]{1,6})?(Z|[+-][0-9]{2}:[0-9]{2})$' THEN
  RETURN false;
 END IF;
 RETURN (v#>>'{}')::timestamptz=(actual#>>'{}')::timestamptz;
EXCEPTION WHEN data_exception THEN
 RETURN false;
END;
$command_time$;

CREATE FUNCTION public.hidra_org_appointment_typed_command_complete() RETURNS trigger
LANGUAGE plpgsql SET search_path=pg_catalog SET timezone='UTC' AS $typed_command$
DECLARE payload jsonb; command jsonb; a jsonb; b jsonb; s jsonb; expected jsonb;
        times text[]; k text; value text; units bigint;
BEGIN
 payload:=pg_catalog.convert_from(NEW.canonical_payload,'UTF8')::jsonb;
 command:=payload->'command'; a:=payload->'after'->'assignment';
 b:=payload->'before'->'assignment'; s:=payload->'successor'->'assignment';
 IF pg_catalog.jsonb_typeof(command) IS DISTINCT FROM 'object'
    OR pg_catalog.jsonb_typeof(a) IS DISTINCT FROM 'object' THEN
  RAISE EXCEPTION 'Invalid typed appointment command' USING ERRCODE='23514';
 END IF;
 expected:=pg_catalog.jsonb_build_object('kind',NEW.operation,'companyPartyId',NEW.company_party_id,
                                       'requestId',command->>'requestId');
 IF NEW.operation='CREATE' THEN
  expected:=expected||pg_catalog.jsonb_build_object(
   'employeeId',a->'employee_id','organizationUnitId',a->'organization_unit_id',
   'positionId',a->'position_id','assignmentType',a->'assignment_type',
   'validFrom',a->'valid_from','validTo',a->'valid_to',
   'expectedBindingRevision',a->'binding_revision','expectedPolicyRevision',a->'policy_revision');
  times:=ARRAY['validFrom','validTo'];
 ELSE
  IF pg_catalog.jsonb_typeof(b) IS DISTINCT FROM 'object'
     OR pg_catalog.jsonb_typeof(command->'reason') IS DISTINCT FROM 'string' THEN
   RAISE EXCEPTION 'Missing appointment source or reason' USING ERRCODE='23514';
  END IF;
  expected:=expected||pg_catalog.jsonb_build_object('assignmentId',a->'id',
                'expectedVersion',b->'appointment_version','reason',command->'reason');
  value:=command->>'reason';
  SELECT coalesce(sum(CASE WHEN pg_catalog.ascii(pg_catalog.substr(value,n,1))>65535 THEN 2 ELSE 1 END),0)
   INTO units FROM pg_catalog.generate_series(1,pg_catalog.char_length(value)) n;
  IF units=0 OR units>2000 OR NOT EXISTS(
      SELECT 1 FROM pg_catalog.generate_series(1,pg_catalog.char_length(value)) n
      WHERE pg_catalog.ascii(pg_catalog.substr(value,n,1))<>ALL(
       ARRAY[9,10,11,12,13,28,29,30,31,32,5760,8192,8193,8194,8195,8196,8197,8198,8200,8201,8202,8232,8233,8287,12288]))
     OR EXISTS(SELECT 1 FROM pg_catalog.generate_series(1,pg_catalog.char_length(value)) n
       WHERE (pg_catalog.ascii(pg_catalog.substr(value,n,1)) BETWEEN 0 AND 31
          AND pg_catalog.ascii(pg_catalog.substr(value,n,1)) NOT IN (9,10))
          OR pg_catalog.ascii(pg_catalog.substr(value,n,1)) BETWEEN 127 AND 159) THEN
   RAISE EXCEPTION 'Invalid appointment change reason' USING ERRCODE='23514';
  END IF;
  IF NEW.operation='END' THEN
   expected:=expected||pg_catalog.jsonb_build_object('endAt',a->'valid_to');
   times:=ARRAY['endAt'];
  ELSIF NEW.operation='TRANSFER' THEN
   IF pg_catalog.jsonb_typeof(s) IS DISTINCT FROM 'object' THEN
    RAISE EXCEPTION 'Missing appointment successor' USING ERRCODE='23514';
   END IF;
   expected:=expected||pg_catalog.jsonb_build_object(
    'destinationOrganizationUnitId',s->'organization_unit_id','destinationPositionId',s->'position_id',
    'destinationType',s->'assignment_type','effectiveAt',s->'valid_from','successorValidTo',s->'valid_to',
    'expectedDestinationBindingRevision',s->'binding_revision',
    'expectedDestinationPolicyRevision',s->'policy_revision');
   times:=ARRAY['effectiveAt','successorValidTo'];
  ELSIF NEW.operation='CORRECT' THEN
   expected:=expected||pg_catalog.jsonb_build_object(
    'correctedOrganizationUnitId',a->'organization_unit_id','correctedPositionId',a->'position_id',
    'correctedType',a->'assignment_type','correctedStatus',a->'status',
    'correctedValidFrom',a->'valid_from','correctedValidTo',a->'valid_to',
    'expectedDestinationBindingRevision',a->'binding_revision',
    'expectedDestinationPolicyRevision',a->'policy_revision');
   times:=ARRAY['correctedValidFrom','correctedValidTo'];
  ELSE
   RAISE EXCEPTION 'Unknown appointment operation' USING ERRCODE='23514';
  END IF;
 END IF;
 -- Exact membership: a null/missing timestamp, coercion, extra key or substituted
 -- scalar may not disappear behind a valid command digest or matching projection.
 IF (command-times) IS DISTINCT FROM (expected-times)
    OR (SELECT pg_catalog.count(*) FROM pg_catalog.jsonb_object_keys(command))
       <>(SELECT pg_catalog.count(*) FROM pg_catalog.jsonb_object_keys(expected))
    OR NOT(command ?& ARRAY(SELECT pg_catalog.jsonb_object_keys(expected))) THEN
  RAISE EXCEPTION 'Appointment command does not match result' USING ERRCODE='23514';
 END IF;
 FOREACH k IN ARRAY times LOOP
  IF public.hidra_org_appointment_command_time_matches(command->k,expected->k) IS DISTINCT FROM true THEN
   RAISE EXCEPTION 'Appointment command time does not match result' USING ERRCODE='23514';
  END IF;
 END LOOP;
 FOR k IN SELECT pg_catalog.jsonb_object_keys(expected) LOOP
  IF k LIKE '%Id' THEN
   value:=command->>k;
   SELECT coalesce(sum(CASE WHEN pg_catalog.ascii(pg_catalog.substr(value,n,1))>65535 THEN 2 ELSE 1 END),0)
    INTO units FROM pg_catalog.generate_series(1,pg_catalog.char_length(value)) n;
   IF NOT public.hidra_org_appointment_exact_id(value) OR units>80 THEN
    RAISE EXCEPTION 'Invalid typed appointment identifier' USING ERRCODE='23514';
   END IF;
  ELSIF k LIKE 'expected%' THEN
   IF pg_catalog.jsonb_typeof(command->k) IS DISTINCT FROM 'number'
      OR (command->>k)!~'^[1-9][0-9]{0,18}$'
      OR (command->>k)::numeric>9223372036854775807
      OR (k='expectedVersion' AND (command->>k)::numeric=9223372036854775807) THEN
    RAISE EXCEPTION 'Invalid typed appointment version' USING ERRCODE='23514';
   END IF;
  END IF;
 END LOOP;
 RETURN NULL;
EXCEPTION WHEN data_exception THEN
 RAISE EXCEPTION 'Invalid typed appointment command value' USING ERRCODE='23514';
END;
$typed_command$;
CREATE CONSTRAINT TRIGGER appointment_command_complete AFTER INSERT ON public.hidra_org_appointment_event
 DEFERRABLE INITIALLY DEFERRED FOR EACH ROW
 EXECUTE FUNCTION public.hidra_org_appointment_typed_command_complete();
REVOKE ALL ON FUNCTION public.hidra_org_appointment_command_time_matches(jsonb,jsonb) FROM PUBLIC;
REVOKE ALL ON FUNCTION public.hidra_org_appointment_typed_command_complete() FROM PUBLIC;
