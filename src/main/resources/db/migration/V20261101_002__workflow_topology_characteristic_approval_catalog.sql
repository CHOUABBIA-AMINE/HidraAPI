-- HAPI-602 S2: exact Workflow target/purpose catalog and canonical authorized locator storage.
-- No Workflow definition, active target/purpose binding, task, approval, role or grant
-- is seeded. Operator-configured genuine Workflow and S3 native approval remain required.
ALTER TABLE public.hidra_workflow_instance
    ALTER COLUMN target_code_snapshot TYPE varchar(2048);

INSERT INTO public.hidra_workflow_type_catalog
    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
SELECT 'topology-installed-characteristic-target-v1','WORKFLOW_TARGET_TYPE',
       'INSTALLED_CHARACTERISTIC_LINK_REVISION',true,0,true,now(),now()
WHERE NOT EXISTS (
 SELECT 1 FROM public.hidra_workflow_type_catalog
 WHERE catalog_name='WORKFLOW_TARGET_TYPE'
   AND code='INSTALLED_CHARACTERISTIC_LINK_REVISION');

INSERT INTO public.hidra_workflow_type_catalog
    (id,catalog_name,code,active,sort_order,system_defined,created_at,updated_at)
SELECT 'topology-installed-characteristic-purpose-v1','WORKFLOW_PURPOSE',
       'INSTALLED_CHARACTERISTIC_LINK_APPROVAL',true,0,true,now(),now()
WHERE NOT EXISTS (
 SELECT 1 FROM public.hidra_workflow_type_catalog
 WHERE catalog_name='WORKFLOW_PURPOSE'
   AND code='INSTALLED_CHARACTERISTIC_LINK_APPROVAL');

-- Do not create an active definition/binding: approval can only use deliberate
-- administrator-configured Workflow definitions and independently authorized humans.
