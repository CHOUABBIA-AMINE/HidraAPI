# HidraAPI P1 Production Runtime

## Status

**IMPLEMENTED CONFIGURATION / PRODUCTION-EQUIVALENT EXERCISE PENDING — HPR-P1-015**

This directory binds the approved Linux VM + systemd + HAProxy application HA design to executable configuration artifacts.

It does not prove that production-equivalent VMs have already been provisioned or that node-loss testing has already run.

## Layout

- `systemd/hidra-api.service` — service unit for each HidraAPI VM.
- `env/hidra.env` — non-secret common production configuration template.
- `env/node-a.env` — node A role; REST + P1 single-active realtime.
- `env/node-b.env` — node B role; REST only; realtime disabled.
- `haproxy/hidra-api.cfg` — two-node REST routing and single-active realtime routing.
- `scripts/drain-node.sh` — planned maintenance traffic drain.
- `scripts/rejoin-node.sh` — return a node to health-check eligibility.
- `scripts/validate-runtime-artifacts.sh` — static CI validation.
- `scripts/verify-application-ha.sh` — production-equivalent one-node-loss exercise and evidence capture.

## Runtime contract

Each VM requires:

- Java 21 at `/usr/bin/java`;
- service account/group `hidra`;
- current application artifact at `/opt/hidra/current/hidra.jar`;
- common runtime file at `/etc/hidra/hidra.env`;
- node-specific role file at `/etc/hidra/node.env`;
- Vault-supplied secret environment file at `/run/hidra/hidra-secrets.env`.

The production startup guard requires production intent/profile and mandatory datasource/security configuration.

The file committed under `env/hidra.env` is a template and deliberately contains no production secrets or real production hostnames.

## REST high availability

HAProxy routes ordinary HTTP/API traffic to two backends:

- `hidra-api-1`;
- `hidra-api-2`.

The backend uses round-robin balancing with no cookie/stick-table/sticky-session rule.

Traffic eligibility is based on:

`GET /actuator/health/readiness`

An unready node is removed from new REST traffic by HAProxy health checks.

## Planned maintenance

On the HAProxy host:

```bash
sudo ops/production/scripts/drain-node.sh hidra-api-1
```

After active requests have drained according to the approved change procedure, stop/update/restart the node through systemd.

After the application is healthy again:

```bash
sudo ops/production/scripts/rejoin-node.sh hidra-api-1
```

HAProxy health checks still determine whether the node actually receives traffic.

## Realtime P1 mode

P1 realtime is deliberately **single-active**:

- node A: `HIDRA_REALTIME_ENABLED=true`;
- node B: `HIDRA_REALTIME_ENABLED=false`;
- HAProxy routes `/api/v1/realtime/ws` and `/api/v1/realtime/sse` only to node A.

`HidraRealtimeConfiguration` is conditionally loaded from `hidra.platform.realtime.enabled`, so disabling realtime on node B now disables the in-process STOMP broker configuration rather than merely setting an unused property.

This is not clustered realtime HA. If node A fails, realtime may be interrupted while ordinary REST remains available through node B.

## Cache safety

P1 retains Spring's process-local cache only for non-authoritative optimization.

The HA implementation does not use cache contents for:

- authentication/authorization authority;
- audit truth;
- distributed coordination;
- correctness-critical business state.

No Redis/shared cache is introduced by HPR-P1-015.

## Request-owned async notification work

The current notification push executor is request-owned asynchronous work, not a distributed scheduler. A single accepted application request invokes the push adapter on the node that processed that request.

Both nodes therefore retain `HIDRA_NOTIFICATION_ASYNC_PUSH_ENABLED=true`. HPR-P1-015 does not create a second scheduler or background poller that would independently claim the same job.

Request retry/idempotency remains an application contract and is not solved by sticky sessions.

## Static validation

Run:

```bash
ops/production/scripts/validate-runtime-artifacts.sh
```

CI executes the same validation. It verifies two REST nodes, one realtime node, readiness health checking, absence of configured session stickiness, node realtime roles, service restart policy, graceful SIGTERM and shell syntax.

## Required one-node-loss exercise

The measured exercise is intentionally explicit and destructive.

Set:

```bash
export HIDRA_HA_BASE_URL=https://approved-hidra-endpoint.example
export HIDRA_APP_NODE_1_BASE_URL=https://approved-hidra-api-1.example
export HIDRA_APP_NODE_2_BASE_URL=https://approved-hidra-api-2.example
export HIDRA_APP_NODE_1_SSH=operator@hidra-api-1
export HIDRA_APP_NODE_2_SSH=operator@hidra-api-2
export HIDRA_HA_ACCEPTANCE_URL=https://approved-hidra-endpoint.example/api/v1/<approved-representative-read>
export HIDRA_HA_ACCEPTANCE_CURL_CONFIG=/run/hidra/ha-acceptance.curlrc
export HIDRA_HA_DESTRUCTIVE_EXERCISE=YES
```

Then run:

```bash
ops/production/scripts/verify-application-ha.sh
```

The script first proves both nodes are directly ready. It then runs a continuous authenticated representative REST request through HAProxy while each node is stopped and restored in turn. The exercise fails on any acceptance-request error, verifies the restarted node directly becomes ready again before the other node may be stopped, and retains both the main evidence log and a per-probe continuity log.

Authentication is supplied through the external `HIDRA_HA_ACCEPTANCE_CURL_CONFIG` file so bearer/session material is not embedded in the script or printed into evidence. The selected acceptance URL must be a safe approved authenticated read operation representative of normal database-backed REST service; the repository intentionally does not invent a business endpoint or credential.

The exercise must run only in an approved production-equivalent environment with authorized sudo/service access.

## Completion evidence

HPR-P1-015 implementation exists in Git, but the roadmap must retain a pending-exercise status until evidence demonstrates:

- both nodes simultaneously active;
- HAProxy routes only to ready nodes;
- loss of either one node preserves continuous authenticated representative REST service, not readiness alone;
- each restored node is directly ready again before the next node-loss step begins;
- no sticky-session correctness requirement;
- realtime remains explicitly single-active;
- node-local cache remains non-authoritative.

The measured result is also required by HPR-P1-012.
