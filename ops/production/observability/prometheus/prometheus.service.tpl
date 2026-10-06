[Unit]
Description=Hidra Prometheus monitoring service
After=network-online.target
Wants=network-online.target

[Service]
Type=simple
User=__PROMETHEUS_USER__
Group=__PROMETHEUS_GROUP__
ExecStart=__PROMETHEUS_BINARY__ --config.file=__PROMETHEUS_CONFIG_FILE__ --storage.tsdb.path=__PROMETHEUS_TSDB_PATH__ --storage.tsdb.retention.time=30d
Restart=on-failure
RestartSec=5s

[Install]
WantedBy=multi-user.target
