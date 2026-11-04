-- HAPI-927 / whole B5. Fresh-schema development qualification; no historical migration rewrite.
CREATE UNIQUE INDEX uq_b5_definition_namespace_key ON hidra_configuration_definition(namespace_id,key);
CREATE UNIQUE INDEX uq_b5_flag_code ON hidra_configuration_feature_flag(code);
CREATE UNIQUE INDEX uq_b5_definition_version ON hidra_configuration_definition_version(definition_id,version_number);
CREATE UNIQUE INDEX uq_b5_active_definition_version ON hidra_configuration_definition_version(definition_id) WHERE active;
CREATE UNIQUE INDEX uq_b5_flag_environment ON hidra_configuration_feature_flag_rule(feature_flag_id,scope_id)
 WHERE active AND scope_type='ENVIRONMENT' AND rule_name LIKE 'HAPI-927:%';

CREATE FUNCTION hidra_b5_configuration_value_guard() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE d hidra_configuration_definition%ROWTYPE; v hidra_configuration_definition_version%ROWTYPE;
BEGIN
 SELECT * INTO d FROM hidra_configuration_definition WHERE id=NEW.definition_id FOR UPDATE;
 SELECT * INTO v FROM hidra_configuration_definition_version WHERE id=NEW.definition_version_id;
 -- Preserve legacy producer contracts. The B5 version marker selects the strict lifecycle.
 IF v.validation_summary IS DISTINCT FROM 'HAPI-927' THEN
   IF EXISTS(SELECT 1 FROM hidra_configuration_definition_version WHERE definition_id=NEW.definition_id AND validation_summary='HAPI-927') THEN
     RAISE EXCEPTION 'Exact active B5 version required' USING ERRCODE='23514';
   END IF;
   RETURN NEW;
 END IF;
 IF TG_OP='UPDATE' THEN RAISE EXCEPTION 'B5 override revisions are append only' USING ERRCODE='23514'; END IF;
 IF d.status<>'ACTIVE' OR d.requires_approval OR d.scoped OR d.sensitivity NOT IN ('PUBLIC','INTERNAL')
   OR NOT v.active OR v.definition_id<>NEW.definition_id OR v.schema_json IS NOT NULL
   OR NEW.status<>'ACTIVE' OR NEW.json_value IS NOT NULL OR NEW.secret_reference IS NOT NULL
   OR NEW.raw_value IS NULL OR length(NEW.raw_value)>2000 OR btrim(NEW.raw_value)<>NEW.raw_value
   OR NEW.created_by_actor_id IS NULL OR btrim(NEW.created_by_actor_id)=''
   OR NEW.environment !~ '^[A-Za-z0-9][A-Za-z0-9_.-]{0,39}$'
   OR NEW.effective_from IS NULL OR NEW.effective_to<=NEW.effective_from THEN
   RAISE EXCEPTION 'Invalid B5 value contract' USING ERRCODE='23514';
 END IF;
 IF (d.value_type='BOOLEAN' AND NEW.raw_value NOT IN ('true','false'))
   OR (d.value_type='NUMBER' AND (length(NEW.raw_value)>58 OR NEW.raw_value !~ '^-?(0|[1-9][0-9]*)(\.[0-9]+)?$'))
   OR d.value_type NOT IN ('STRING','BOOLEAN','NUMBER') THEN
   RAISE EXCEPTION 'Invalid B5 typed value' USING ERRCODE='23514';
 END IF;
 IF d.value_type='NUMBER' AND (length(replace(ltrim(NEW.raw_value,'-'),'.',''))>38
   OR (position('.' in NEW.raw_value)>0 AND length(split_part(NEW.raw_value,'.',2))>18)) THEN
   RAISE EXCEPTION 'B5 decimal bounds exceeded' USING ERRCODE='23514';
 END IF;
 IF EXISTS(SELECT 1 FROM hidra_configuration_value x WHERE x.definition_id=NEW.definition_id
   AND x.environment=NEW.environment AND x.status='ACTIVE'
   AND (x.effective_to IS NULL OR x.effective_to>NEW.effective_from)
   AND (NEW.effective_to IS NULL OR x.effective_from IS NULL OR x.effective_from<NEW.effective_to)) THEN
   RAISE EXCEPTION 'Overlapping B5 override' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_b5_configuration_value BEFORE INSERT OR UPDATE ON hidra_configuration_value
 FOR EACH ROW EXECUTE FUNCTION hidra_b5_configuration_value_guard();

CREATE FUNCTION hidra_b5_definition_immutable_guard() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 IF TG_TABLE_NAME='hidra_configuration_definition_version' THEN
   IF OLD.validation_summary='HAPI-927' THEN RAISE EXCEPTION 'B5 definition versions are immutable' USING ERRCODE='23514'; END IF;
 ELSE
   IF OLD.status='ACTIVE' AND EXISTS(SELECT 1 FROM hidra_configuration_definition_version WHERE definition_id=OLD.id AND validation_summary='HAPI-927') THEN
     RAISE EXCEPTION 'Active B5 definitions are immutable' USING ERRCODE='23514';
   END IF;
 END IF;
 IF TG_OP='DELETE' THEN RETURN OLD; END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER tr_b5_definition_version_immutable BEFORE UPDATE OR DELETE ON hidra_configuration_definition_version
 FOR EACH ROW EXECUTE FUNCTION hidra_b5_definition_immutable_guard();
CREATE TRIGGER tr_b5_definition_immutable BEFORE UPDATE OR DELETE ON hidra_configuration_definition
 FOR EACH ROW EXECUTE FUNCTION hidra_b5_definition_immutable_guard();
