apiVersion: 1

datasources:
  - name: Prometheus
    uid: hidra-prometheus
    type: prometheus
    access: proxy
    url: __PROMETHEUS_URL__
    isDefault: true

  - name: Loki
    uid: hidra-loki
    type: loki
    access: proxy
    url: __LOKI_URL__
