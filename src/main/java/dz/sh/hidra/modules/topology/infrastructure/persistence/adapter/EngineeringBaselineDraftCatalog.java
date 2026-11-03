/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Pins the additive baseline trigger and private lock function catalog.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

final class EngineeringBaselineDraftCatalog {
    private EngineeringBaselineDraftCatalog(){}
    static final String PREDICATE="""
        EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND p.pronargs=0 AND p.prokind='f' AND p.prorettype='trigger'::regtype AND NOT p.prosecdef AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='d5bcc6c469bb9af221e351abce89ba60939068b6191a5034fd1fd2335501daf8')
        AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_topology_baseline_original_transaction_guard()'::regprocedure AND p.pronargs=0 AND p.prokind='f' AND p.prorettype='trigger'::regtype AND NOT p.prosecdef AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='5a24c678f68e8b48c524222bd6cf2d0762761a77467ab0a2e20de069983e8574')
        AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_topology_baseline_head_guard()'::regprocedure AND p.pronargs=0 AND p.prokind='f' AND p.prorettype='trigger'::regtype AND NOT p.prosecdef AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='b3b03d54c6987f65ec65859a32a56beb62e6e88fae5c3adf7dc042c99b46b394')
        AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_topology_baseline_lock_storage()'::regprocedure AND p.pronargs=0 AND p.prokind='f' AND p.prorettype='boolean'::regtype AND p.prosecdef AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='aecc3f8e809ef1e8609d5c6946a75210beb0b53c4b1b53d6122e4df7d46182fc')
        AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_topology_baseline_lock_sources()'::regprocedure AND p.pronargs=0 AND p.prokind='f' AND p.prorettype='boolean'::regtype AND p.prosecdef AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='3aebc9d1b4d36f986349403bb0ed8709ba602f2cec12cae4d8b91c6546573dce')
        AND EXISTS(SELECT 1 FROM pg_proc p WHERE p.oid='public.hidra_custody_baseline_lock_product_source()'::regprocedure AND p.pronargs=0 AND p.prokind='f' AND p.prorettype='boolean'::regtype AND p.prosecdef AND NOT pg_has_role(current_user,p.proowner,'MEMBER') AND p.proconfig=ARRAY['search_path=pg_catalog, pg_temp'] AND encode(sha256(convert_to(p.prosrc,'UTF8')),'hex')='af427213d0f804a411505c2d7f87c5a82bde2b49f41da0cc0e80000ad2b8a848')
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_revision'::regclass AND t.tgname='trg_topology_baseline_revision_origin' AND t.tgfoid='public.hidra_topology_baseline_original_transaction_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=7 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_request'::regclass AND t.tgname='trg_topology_baseline_request_origin' AND t.tgfoid='public.hidra_topology_baseline_original_transaction_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=7 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_revision'::regclass AND t.tgname='trg_topology_baseline_revision_immutable' AND t.tgfoid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=27 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_request'::regclass AND t.tgname='trg_topology_baseline_request_immutable' AND t.tgfoid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=27 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_draft'::regclass AND t.tgname='trg_topology_baseline_head_transition' AND t.tgfoid='public.hidra_topology_baseline_head_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=19 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_draft'::regclass AND t.tgname='trg_topology_baseline_head_delete' AND t.tgfoid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=11 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_draft'::regclass AND t.tgname='trg_topology_baseline_draft_truncate' AND t.tgfoid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=34 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_revision'::regclass AND t.tgname='trg_topology_baseline_revision_truncate' AND t.tgfoid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=34 AND NOT t.tgisinternal)
        AND EXISTS(SELECT 1 FROM pg_trigger t WHERE t.tgrelid='public.hidra_topology_engineering_baseline_request'::regclass AND t.tgname='trg_topology_baseline_request_truncate' AND t.tgfoid='public.hidra_topology_baseline_immutable_guard()'::regprocedure AND t.tgenabled='O' AND t.tgtype=34 AND NOT t.tgisinternal)
        -- Preserve owner composite keys and validated constraints, not only triggers.
        AND (SELECT count(*)=3 FROM pg_catalog.pg_constraint c
            WHERE c.contype='p' AND c.convalidated AND c.conrelid IN (
              'public.hidra_topology_engineering_baseline_draft'::regclass,
              'public.hidra_topology_engineering_baseline_revision'::regclass,
              'public.hidra_topology_engineering_baseline_request'::regclass))
        AND (SELECT count(*)=4 FROM pg_catalog.pg_constraint c
            WHERE c.contype='f' AND c.convalidated AND c.conrelid IN (
              'public.hidra_topology_engineering_baseline_draft'::regclass,
              'public.hidra_topology_engineering_baseline_revision'::regclass,
              'public.hidra_topology_engineering_baseline_request'::regclass))
        -- Require the actual exact owned key columns and references. An unrelated
        -- replacement constraint cannot satisfy the original count predicates.
        AND (SELECT count(*)=3 FROM (VALUES
            ('public.hidra_topology_engineering_baseline_draft',
                ARRAY['company_party_id','pipeline_system_id','manifest_id']::text[]),
            ('public.hidra_topology_engineering_baseline_revision',
                ARRAY['company_party_id','pipeline_system_id','manifest_id','version']::text[]),
            ('public.hidra_topology_engineering_baseline_request',
                ARRAY['company_party_id','pipeline_system_id','manifest_id','actor_user_id','request_id']::text[])
            ) AS expected(rel,columns)
            WHERE EXISTS(SELECT 1 FROM pg_catalog.pg_constraint c
                WHERE c.conrelid=expected.rel::regclass AND c.contype='p' AND c.convalidated
                AND (SELECT array_agg(a.attname::text ORDER BY k.ord)
                     FROM unnest(c.conkey) WITH ORDINALITY AS k(number,ord)
                     JOIN pg_catalog.pg_attribute a ON a.attrelid=c.conrelid AND a.attnum=k.number)
                    =expected.columns))
        AND (SELECT count(*)=4 FROM (VALUES
            ('public.hidra_topology_engineering_baseline_draft','public.hidra_topology_pipeline_system',
                ARRAY['pipeline_system_id']::text[],ARRAY['id']::text[]),
            ('public.hidra_topology_engineering_baseline_draft','public.hidra_topology_engineering_baseline_revision',
                ARRAY['company_party_id','pipeline_system_id','manifest_id','head_version']::text[],
                ARRAY['company_party_id','pipeline_system_id','manifest_id','version']::text[]),
            ('public.hidra_topology_engineering_baseline_revision','public.hidra_topology_engineering_baseline_draft',
                ARRAY['company_party_id','pipeline_system_id','manifest_id']::text[],
                ARRAY['company_party_id','pipeline_system_id','manifest_id']::text[]),
            ('public.hidra_topology_engineering_baseline_request','public.hidra_topology_engineering_baseline_revision',
                ARRAY['company_party_id','pipeline_system_id','manifest_id','version']::text[],
                ARRAY['company_party_id','pipeline_system_id','manifest_id','version']::text[])
            ) AS expected(rel,referenced,columns,ref_columns)
            WHERE EXISTS(SELECT 1 FROM pg_catalog.pg_constraint c
                WHERE c.conrelid=expected.rel::regclass AND c.confrelid=expected.referenced::regclass
                    AND c.contype='f' AND c.convalidated
                    AND (SELECT array_agg(a.attname::text ORDER BY k.ord)
                         FROM unnest(c.conkey) WITH ORDINALITY AS k(number,ord)
                         JOIN pg_catalog.pg_attribute a ON a.attrelid=c.conrelid AND a.attnum=k.number)
                        =expected.columns
                    AND (SELECT array_agg(a.attname::text ORDER BY k.ord)
                         FROM unnest(c.confkey) WITH ORDINALITY AS k(number,ord)
                         JOIN pg_catalog.pg_attribute a ON a.attrelid=c.confrelid AND a.attnum=k.number)
                        =expected.ref_columns))
        AND NOT EXISTS(SELECT 1 FROM pg_catalog.pg_constraint c
            WHERE c.conrelid IN (
              'public.hidra_topology_engineering_baseline_draft'::regclass,
              'public.hidra_topology_engineering_baseline_revision'::regclass,
              'public.hidra_topology_engineering_baseline_request'::regclass)
              AND NOT c.convalidated)
        """;
}
