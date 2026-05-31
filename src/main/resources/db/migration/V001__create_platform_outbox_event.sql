create table hidra_platform_outbox_event (
    id uuid not null,
    event_id varchar(120) not null,
    event_type varchar(255) not null,
    aggregate_id varchar(120),
    aggregate_type varchar(255),
    payload text not null,
    occurred_at timestamp with time zone not null,
    status varchar(40) not null,
    retry_count integer not null,
    last_error text,
    created_at timestamp with time zone not null,
    published_at timestamp with time zone,
    constraint pk_hidra_platform_outbox_event primary key (id),
    constraint uk_hidra_platform_outbox_event_event_id unique (event_id)
);

create index idx_hidra_platform_outbox_event_status_created_at
    on hidra_platform_outbox_event (status, created_at);

create index idx_hidra_platform_outbox_event_occurred_at
    on hidra_platform_outbox_event (occurred_at);
