#!/usr/bin/env python3
"""Prove the exact HAPI-923 exception without suppressing other contract failures."""
import contextlib
import copy
import io
import json
import sys
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

sys.dont_write_bytecode = True
import openapi_compatibility as checker

ROUTE = "/api/v1/identity/users/{id}/local-credential"
STATE = ROUTE + "/state"
OLD_REF = {"$ref": "#/components/schemas/Credential"}
NEW_REF = {"$ref": "#/components/schemas/IdentityLocalCredentialState"}


def operation():
    return {"security": [{"hidraBearerJwt": []}], "responses": {
        "200": {"content": {"*/*": {"schema": copy.deepcopy(OLD_REF)}}}}}


def fixture():
    base = {"openapi": "3.1.0", "paths": {ROUTE: {"put": operation()}, STATE: {"get": operation()}},
            "components": {"schemas": {"Credential": {
                "type": "object", "required": ["password", "reason"],
                "properties": {"password": {"type": "string", "minLength": 1},
                               "reason": {"type": "string", "minLength": 1},
                               "expectedSecurityVersion": {"type": "integer", "format": "int64", "minimum": 0}}}}}}
    base["paths"][ROUTE]["put"]["requestBody"] = {
        "required": True, "content": {"application/json": {"schema": copy.deepcopy(OLD_REF)}}}
    current = copy.deepcopy(base)
    current["components"]["schemas"]["IdentityLocalCredentialState"] = copy.deepcopy(checker.REVIEWED_CREDENTIAL_STATE)
    for route, method in ((ROUTE, "put"), (STATE, "get")):
        current["paths"][route][method]["responses"]["200"]["content"]["*/*"]["schema"] = copy.deepcopy(NEW_REF)
    return base, current


class CredentialCompatibilityTests(unittest.TestCase):
    def setUp(self):
        self.base, self.current = fixture()
        # Synthetic baseline for isolated tests; production retains the literal
        # successful Full1121 digest and is separately exercised on real artifacts.
        pin = patch.object(checker, "REVIEWED_CREDENTIAL_BASE_SHA256", checker.contract_digest(self.base))
        pin.start()
        self.addCleanup(pin.stop)

    def compare(self, base=None, current=None):
        with tempfile.TemporaryDirectory() as directory:
            paths = [Path(directory) / name for name in ("base.json", "current.json")]
            for path, document in zip(paths, (self.base if base is None else base,
                                               self.current if current is None else current)):
                path.write_text(json.dumps(document))
            stdout, stderr = io.StringIO(), io.StringIO()
            with patch.object(sys, "argv", ["openapi_compatibility.py", *map(str, paths)]), \
                    contextlib.redirect_stdout(stdout), contextlib.redirect_stderr(stderr):
                code = checker.main()
            return code, stdout.getvalue(), stderr.getvalue()

    def test_only_reviewed_two_responses_are_accepted_and_reported(self):
        code, output, errors = self.compare()
        self.assertEqual(code, 0, errors)
        self.assertEqual(output.count("Owner-approved HAPI-923 schema correction:"), 2)

    def test_source_stamp_does_not_replace_base_document_identity(self):
        self.base["x-hidra-ci-source-sha"] = "a" * 40
        self.assertEqual(self.compare()[0], 0)
        self.base["info"] = {"title": "Changed baseline"}
        self.assertEqual(self.compare()[0], 1)

    def test_only_exact_artifact_and_regenerated_server_variants_are_pinned(self):
        artifact = copy.deepcopy(self.base)
        artifact["servers"] = [{"url": "http://127.0.0.1:8080", "description": "Generated server url"}]
        regenerated = copy.deepcopy(artifact)
        regenerated["servers"][0]["url"] = "http://127.0.0.1:8081"
        unknown = copy.deepcopy(artifact)
        unknown["servers"][0]["url"] = "http://127.0.0.1:8082"
        with patch.object(checker, "REVIEWED_CREDENTIAL_BASE_SHA256", checker.contract_digest(artifact)), \
                patch.object(checker, "REVIEWED_CREDENTIAL_REGENERATED_BASE_SHA256", checker.contract_digest(regenerated)):
            self.assertEqual(self.compare(artifact, self.current)[0], 0)
            self.assertEqual(self.compare(regenerated, self.current)[0], 0)
            code, output, errors = self.compare(unknown, self.current)
            self.assertEqual(code, 1)
            self.assertNotIn("Owner-approved", output)
            self.assertIn("response properties removed", errors)
            regenerated["components"]["schemas"]["Credential"]["description"] = "Changed contract"
            self.assertEqual(self.compare(regenerated, self.current)[0], 1)

    def test_wrong_route_method_status_media_or_ref_is_not_exempt(self):
        for kind in ("route", "method", "status", "media", "ref"):
            with self.subTest(kind=kind):
                base, current = fixture()
                if kind == "route":
                    for doc in (base, current):
                        doc["paths"]["/api/v1/identity/other"] = doc["paths"].pop(ROUTE)
                elif kind == "method":
                    for doc in (base, current):
                        doc["paths"][ROUTE]["post"] = doc["paths"][ROUTE].pop("put")
                elif kind == "status":
                    for doc in (base, current):
                        op = doc["paths"][ROUTE]["put"]
                        op["responses"]["201"] = op["responses"].pop("200")
                elif kind == "media":
                    for doc in (base, current):
                        content = doc["paths"][ROUTE]["put"]["responses"]["200"]["content"]
                        content["application/json"] = content.pop("*/*")
                else:
                    current["components"]["schemas"]["Another"] = current["components"]["schemas"]["IdentityLocalCredentialState"]
                    current["paths"][ROUTE]["put"]["responses"]["200"]["content"]["*/*"]["schema"] = {"$ref": "#/components/schemas/Another"}
                # Isolate the scope restriction from the independent base hash guard.
                with patch.object(checker, "REVIEWED_CREDENTIAL_BASE_SHA256", checker.contract_digest(base)):
                    self.assertEqual(self.compare(base, current)[0], 1)

    def test_changed_state_schema_is_not_exempt(self):
        for kind in ("type", "missing", "extra", "required"):
            with self.subTest(kind=kind):
                current = copy.deepcopy(self.current)
                state = current["components"]["schemas"]["IdentityLocalCredentialState"]
                if kind == "type":
                    state["properties"]["securityVersion"]["type"] = "string"
                elif kind == "missing":
                    del state["properties"]["changedAt"]
                elif kind == "extra":
                    state["properties"]["password"] = {"type": "string"}
                else:
                    state["required"] = ["userId"]
                self.assertEqual(self.compare(current=current)[0], 1)

    def test_changed_request_schema_is_not_exempt(self):
        self.current["components"]["schemas"]["Credential"]["properties"]["password"]["minLength"] = 2
        self.assertEqual(self.compare()[0], 1)

    def test_new_required_parameter_still_fails(self):
        self.current["paths"][ROUTE]["put"]["parameters"] = [{"name": "extra", "in": "query", "required": True}]
        code, _, errors = self.compare()
        self.assertEqual(code, 1)
        self.assertIn("new required parameter", errors)

    def test_new_required_input_property_still_fails(self):
        self.current["components"]["schemas"]["Credential"]["required"].append("expectedSecurityVersion")
        code, _, errors = self.compare()
        self.assertEqual(code, 1)
        self.assertIn("request properties became required", errors)

    def test_unrelated_response_property_loss_still_fails(self):
        self.base["paths"]["/api/v1/other"] = {"get": {"responses": {"200": {"content": {
            "application/json": {"schema": {"type": "object", "properties": {"id": {"type": "string"}}}}}}}}}
        self.current["paths"]["/api/v1/other"] = copy.deepcopy(self.base["paths"]["/api/v1/other"])
        self.current["paths"]["/api/v1/other"]["get"]["responses"]["200"]["content"]["application/json"]["schema"]["properties"] = {}
        with patch.object(checker, "REVIEWED_CREDENTIAL_BASE_SHA256", checker.contract_digest(self.base)):
            code, _, errors = self.compare()
        self.assertEqual(code, 1)
        self.assertIn("response properties removed", errors)

    def test_removed_operation_still_fails(self):
        del self.current["paths"][ROUTE]["put"]
        self.assertEqual(self.compare()[0], 1)

    def test_removed_response_code_and_media_still_fail(self):
        for kind in ("code", "media"):
            with self.subTest(kind=kind):
                current = copy.deepcopy(self.current)
                response = current["paths"][ROUTE]["put"]["responses"]
                if kind == "code":
                    del response["200"]
                else:
                    response["200"]["content"] = {}
                self.assertEqual(self.compare(current=current)[0], 1)

    def test_security_check_remains_active(self):
        self.base["paths"][ROUTE]["put"]["security"] = []
        with patch.object(checker, "REVIEWED_CREDENTIAL_BASE_SHA256", checker.contract_digest(self.base)):
            code, _, errors = self.compare()
        self.assertEqual(code, 1)
        self.assertIn("public to authenticated", errors)

    def test_corrected_successful_base_uses_normal_future_response_checks(self):
        base = copy.deepcopy(self.current)
        self.assertEqual(self.compare(base, self.current)[0], 0)
        current = copy.deepcopy(base)
        del current["components"]["schemas"]["IdentityLocalCredentialState"]["properties"]["securityVersion"]
        code, output, errors = self.compare(base, current)
        self.assertEqual(code, 1)
        self.assertNotIn("Owner-approved", output)
        self.assertIn("securityVersion", errors)


if __name__ == "__main__":
    unittest.main()
