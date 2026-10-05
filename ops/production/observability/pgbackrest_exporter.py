#!/usr/bin/env python3
import json
import os
import subprocess
import time
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer

CONFIG = os.environ.get("HIDRA_PGBACKREST_CONFIG", "/etc/pgbackrest/pgbackrest.conf")
HOST = os.environ.get("HIDRA_PGBACKREST_EXPORTER_BIND", "127.0.0.1")
PORT = int(os.environ.get("HIDRA_PGBACKREST_EXPORTER_PORT", "9188"))


def run_info():
    result = subprocess.run(
        ["pgbackrest", f"--config={CONFIG}", "--stanza=hidra", "info", "--output=json"],
        check=True,
        capture_output=True,
        text=True,
        timeout=30,
    )
    payload = json.loads(result.stdout)
    backups = payload[0].get("backup", []) if payload else []
    completed = [b for b in backups if b.get("timestamp", {}).get("stop")]
    if not completed:
        return None
    return max(completed, key=lambda b: b["timestamp"]["stop"])


def run_check():
    result = subprocess.run(
        ["pgbackrest", f"--config={CONFIG}", "--stanza=hidra", "check"],
        capture_output=True,
        text=True,
        timeout=60,
    )
    return 1 if result.returncode == 0 else 0


def render_metrics():
    now = time.time()
    latest = run_info()
    age = -1 if latest is None else max(0, now - latest["timestamp"]["stop"])
    check = run_check()
    lines = [
        "# HELP hidra_pgbackrest_latest_backup_age_seconds Age of latest completed pgBackRest backup.",
        "# TYPE hidra_pgbackrest_latest_backup_age_seconds gauge",
        f"hidra_pgbackrest_latest_backup_age_seconds {age:.0f}",
        "# HELP hidra_pgbackrest_check_success Whether pgBackRest stanza check succeeds.",
        "# TYPE hidra_pgbackrest_check_success gauge",
        f"hidra_pgbackrest_check_success {check}",
    ]
    return "\n".join(lines) + "\n"


class Handler(BaseHTTPRequestHandler):
    def do_GET(self):
        if self.path != "/metrics":
            self.send_response(404)
            self.end_headers()
            return
        try:
            body = render_metrics().encode("utf-8")
            self.send_response(200)
        except Exception as exc:
            body = (
                "# HELP hidra_pgbackrest_exporter_error Exporter collection failure.\n"
                "# TYPE hidra_pgbackrest_exporter_error gauge\n"
                "hidra_pgbackrest_exporter_error 1\n"
            ).encode("utf-8")
            self.send_response(500)
            print(f"pgBackRest exporter error: {exc}", flush=True)
        self.send_header("Content-Type", "text/plain; version=0.0.4")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def log_message(self, fmt, *args):
        return


if __name__ == "__main__":
    ThreadingHTTPServer((HOST, PORT), Handler).serve_forever()
