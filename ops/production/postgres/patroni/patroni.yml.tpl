# HidraAPI Patroni production template.
# Render per PostgreSQL node. Secret values come from the approved Vault/runtime mechanism.
scope: __HIDRA_PATRONI_SCOPE__
namespace: /hidra/postgresql/
name: __HIDRA_PATRONI_NODE_NAME__

restapi:
  listen: __PATRONI_REST_LISTEN__
  connect_address: __PATRONI_REST_CONNECT_ADDRESS__
  certfile: __PATRONI_REST_TLS_CERT__
  keyfile: __PATRONI_REST_TLS_KEY__
  cafile: __PATRONI_REST_TLS_CA__
  verify_client: required

etcd3:
  hosts:
    - __ETCD_NODE_1__:2379
    - __ETCD_NODE_2__:2379
    - __ETCD_NODE_3__:2379
  protocol: https
  cacert: __ETCD_CA_CERT__
  cert: __ETCD_CLIENT_CERT__
  key: __ETCD_CLIENT_KEY__

bootstrap:
  dcs:
    ttl: 30
    loop_wait: 10
    retry_timeout: 10
    maximum_lag_on_failover: __PATRONI_MAXIMUM_LAG_ON_FAILOVER_BYTES__
    synchronous_mode: __PATRONI_SYNCHRONOUS_MODE__
    synchronous_mode_strict: __PATRONI_SYNCHRONOUS_MODE_STRICT__
    postgresql:
      use_pg_rewind: true
      use_slots: true
      parameters:
        wal_level: replica
        hot_standby: "on"
        max_wal_senders: __POSTGRES_MAX_WAL_SENDERS__
        max_replication_slots: __POSTGRES_MAX_REPLICATION_SLOTS__
        wal_keep_size: __POSTGRES_WAL_KEEP_SIZE__
  initdb:
    - encoding: UTF8
    - data-checksums
  pg_hba:
    - hostssl replication __PATRONI_REPLICATION_USER__ __HIDRA_REPLICATION_CIDR__ scram-sha-256
    - hostssl __HIDRA_DATABASE__ __HIDRA_DATABASE_USER__ __HIDRA_APPLICATION_CIDR__ scram-sha-256

postgresql:
  listen: __POSTGRES_LISTEN__
  connect_address: __POSTGRES_CONNECT_ADDRESS__
  data_dir: __POSTGRES_DATA_DIR__
  bin_dir: __POSTGRES_BIN_DIR__
  authentication:
    replication:
      username: __PATRONI_REPLICATION_USER__
      password: __PATRONI_REPLICATION_PASSWORD__
    superuser:
      username: __POSTGRES_SUPERUSER__
      password: __POSTGRES_SUPERUSER_PASSWORD__
  parameters:
    ssl: "on"
    ssl_cert_file: __POSTGRES_TLS_CERT__
    ssl_key_file: __POSTGRES_TLS_KEY__
    ssl_ca_file: __POSTGRES_TLS_CA__
    password_encryption: scram-sha-256

tags:
  nofailover: false
  noloadbalance: false
  clonefrom: false
  nosync: false
