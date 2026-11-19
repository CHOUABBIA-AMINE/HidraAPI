-- T27: one transactional WEB inbox receipt for each opt-in Workflow report review task.
-- Notification owns both immutable tables. No source-owner foreign keys or grants are seeded.
INSERT INTO public.hidra_identity_permission
 (id,code,permission_domain,resource_type,action,sensitive,status,created_at,updated_at)
VALUES
 ('t27-inbox-read','notification:inbox:read','notification','NOTIFICATION_INBOX','read',true,'ACTIVE',now(),now()),
 ('t27-inbox-acknowledge','notification:inbox:acknowledge','notification','NOTIFICATION_INBOX','acknowledge',true,'ACTIVE',now(),now());

CREATE TABLE public.hidra_notification_t27_inbox_item (
 id varchar(36) PRIMARY KEY CHECK(id ~ '^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$'),
 event_code varchar(70) NOT NULL CHECK(event_code='WORKFLOW_REPORT_REVIEW_TASK_ASSIGNED_V1'),
 message_code varchar(70) NOT NULL CHECK(message_code='REPORT_REVIEW_TASK_ASSIGNED_V1'),
 channel varchar(20) NOT NULL CHECK(channel='WEB'),
 delivery_status varchar(20) NOT NULL CHECK(delivery_status='DELIVERED'),
 company_id varchar(96) NOT NULL CHECK(btrim(company_id)<>'' AND company_id=btrim(company_id)),
 facility_id varchar(120) NOT NULL CHECK(btrim(facility_id)<>'' AND facility_id=btrim(facility_id)),
 recipient_actor_id varchar(80) NOT NULL CHECK(btrim(recipient_actor_id)<>'' AND recipient_actor_id=btrim(recipient_actor_id)),
 workflow_task_id varchar(80) NOT NULL CHECK(btrim(workflow_task_id)<>'' AND workflow_task_id=btrim(workflow_task_id)),
 assigned_at timestamptz NOT NULL,
 delivered_at timestamptz NOT NULL CHECK(delivered_at>=assigned_at),
 CONSTRAINT t27_notification_event_unique UNIQUE(event_code,workflow_task_id),
 CONSTRAINT t27_notification_recipient_unique UNIQUE(id,recipient_actor_id)
);
CREATE INDEX t27_inbox_keyset ON public.hidra_notification_t27_inbox_item
 (company_id,facility_id,recipient_actor_id,delivered_at DESC,id DESC);

CREATE TABLE public.hidra_notification_t27_inbox_acknowledgement (
 notification_id varchar(36) PRIMARY KEY,
 actor_id varchar(80) NOT NULL CHECK(btrim(actor_id)<>'' AND actor_id=btrim(actor_id)),
 acknowledged_at timestamptz NOT NULL,
 CONSTRAINT t27_ack_original_recipient FOREIGN KEY(notification_id,actor_id)
     REFERENCES public.hidra_notification_t27_inbox_item(id,recipient_actor_id)
);

CREATE FUNCTION public.t27_notification_no_mutation() RETURNS trigger LANGUAGE plpgsql AS $$
BEGIN
 RAISE EXCEPTION 'T27 immutable Notification evidence cannot be changed' USING ERRCODE='23514';
END $$;
CREATE TRIGGER t27_inbox_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_notification_t27_inbox_item FOR EACH ROW EXECUTE FUNCTION public.t27_notification_no_mutation();
CREATE TRIGGER t27_inbox_no_truncate BEFORE TRUNCATE
 ON public.hidra_notification_t27_inbox_item FOR EACH STATEMENT EXECUTE FUNCTION public.t27_notification_no_mutation();
CREATE TRIGGER t27_ack_immutable BEFORE UPDATE OR DELETE
 ON public.hidra_notification_t27_inbox_acknowledgement FOR EACH ROW EXECUTE FUNCTION public.t27_notification_no_mutation();
CREATE TRIGGER t27_ack_no_truncate BEFORE TRUNCATE
 ON public.hidra_notification_t27_inbox_acknowledgement FOR EACH STATEMENT EXECUTE FUNCTION public.t27_notification_no_mutation();

CREATE FUNCTION public.t27_notification_ack_proof() RETURNS trigger LANGUAGE plpgsql AS $$
DECLARE delivered timestamptz; recipient varchar(80);
BEGIN
 SELECT i.delivered_at,i.recipient_actor_id INTO delivered,recipient
   FROM public.hidra_notification_t27_inbox_item i
   WHERE i.id=NEW.notification_id FOR UPDATE;
 IF NOT FOUND OR NEW.actor_id IS DISTINCT FROM recipient OR NEW.acknowledged_at<delivered THEN
   RAISE EXCEPTION 'T27 acknowledgement must match original recipient and delivery' USING ERRCODE='23514';
 END IF;
 RETURN NEW;
END $$;
CREATE TRIGGER t27_ack_proof BEFORE INSERT
 ON public.hidra_notification_t27_inbox_acknowledgement FOR EACH ROW EXECUTE FUNCTION public.t27_notification_ack_proof();
