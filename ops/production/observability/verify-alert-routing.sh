#!/usr/bin/env bash
set -euo pipefail

: "${HIDRA_ALERTMANAGER_URL:?Set HIDRA_ALERTMANAGER_URL.}"

post_alert() {
  local name="$1"
  local severity="$2"
  local domain="$3"
  curl --fail --silent --show-error     --header 'Content-Type: application/json'     --data "[{
      \"labels\": {
        \"alertname\": \"${name}\",
        \"severity\": \"${severity}\",
        \"domain\": \"${domain}\",
        \"owner\": \"hpr-p1-019-verification\"
      },
      \"annotations\": {
        \"summary\": \"Synthetic HPR-P1-019 routing verification alert\"
      }
    }]"     "${HIDRA_ALERTMANAGER_URL%/}/api/v2/alerts"
}

post_alert HidraSyntheticWarning warning application
post_alert HidraSyntheticCritical critical application
post_alert HidraSyntheticDatabase critical database
post_alert HidraSyntheticSecurity critical security

echo "Synthetic alerts accepted by Alertmanager."
echo "Receiver delivery/escalation must be confirmed from the configured production-equivalent destinations."
