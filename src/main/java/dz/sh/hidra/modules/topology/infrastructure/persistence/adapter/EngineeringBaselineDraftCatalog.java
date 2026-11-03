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
        """;
}
