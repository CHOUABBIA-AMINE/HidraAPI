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



class InvestigationCompatibilityTests(unittest.TestCase):
    compare = CredentialCompatibilityTests.compare

    def setUp(self):
        string = {"type": "string"}
        base_schemas = {
            "View": {"type": "object", "properties": {
                "caseId": string, "caseStatus": string, "candidateId": string, "disposition": string,
                "history": {"type": "array", "items": checker.investigation_ref("Event")},
            }},
            "Event": {"type": "object", "properties": {"operation": string, "recordedAt": string}},
            "Receipt": {"type": "object", "properties": {"payloadSha256": string, "revisionId": string}},
            "Evidence": {"type": "object", "required": ["direction", "evaluationId", "explanation", "requestId"],
                         "properties": {field: {"type": "string", "minLength": 1}
                                        for field in ("direction", "evaluationId", "explanation", "requestId")}},
        }
        self.base = {"openapi": "3.1.0", "paths": {}, "components": {"schemas": base_schemas}}
        for (route, method), (old_name, _) in checker.INVESTIGATION_RESPONSES.items():
            self.base["paths"].setdefault(route, {})[method] = {
                "security": [{"hidraBearerJwt": []}],
                "responses": {"200": {"content": {"*/*": {"schema": checker.investigation_ref(old_name)}}}},
            }
        evidence = checker.INCIDENT_INVESTIGATION + "/evidence"
        self.base["paths"][evidence]["post"]["requestBody"] = {
            "required": True, "content": {"application/json": {"schema": checker.investigation_ref("Evidence")}}}
        self.base["paths"]["/other-owner"] = {"post": {
            "responses": {"200": {"content": {"*/*": {"schema": checker.investigation_ref("Receipt")}}}}}}
        self.current = copy.deepcopy(self.base)
        schemas = self.current["components"]["schemas"]
        for owner in ("IncidentInvestigation", "LeakInvestigation"):
            for kind in ("View", "Event", "Receipt", "EvidenceRequest"):
                name = owner + kind
                schemas[name] = copy.deepcopy(checker.reviewed_investigation_schema(base_schemas, name))
        for (route, method), (_, new_name) in checker.INVESTIGATION_RESPONSES.items():
            self.current["paths"][route][method]["responses"]["200"]["content"]["*/*"]["schema"] = checker.investigation_ref(new_name)
        self.current["paths"][evidence]["post"]["requestBody"]["content"]["application/json"]["schema"] = checker.investigation_ref("IncidentInvestigationEvidenceRequest")
        pin = patch.object(checker, "REVIEWED_INVESTIGATION_BASES", frozenset({checker.contract_digest(self.base)}))
        pin.start()
        self.addCleanup(pin.stop)

    def test_exact_investigation_corrections_and_existing_compatibility(self):
        code, output, errors = self.compare()
        self.assertEqual(code, 0, errors)
        self.assertEqual(output.count("Owner-approved T62 schema correction:"), len(checker.INVESTIGATION_RESPONSES) + 1)
        schemas = self.current["components"]["schemas"]
        self.assertEqual(set(schemas["IncidentInvestigationReceipt"]["properties"]),
                         {"incidentId", "eventId", "operation", "version", "investigationState", "actorId", "recordedAt", "replayed"})
        self.assertIn("sourceType", schemas["IncidentInvestigationEvidenceRequest"]["required"])
        self.assertNotIn("evaluationId", schemas["IncidentInvestigationEvidenceRequest"]["properties"])

    def test_wrong_base_or_wrong_nested_shape_is_rejected(self):
        changed = copy.deepcopy(self.base)
        changed["info"] = {"title": "Unknown base"}
        self.assertEqual(self.compare(base=changed)[0], 1)
        for name, field in (("IncidentInvestigationView", "incidentId"),
                            ("IncidentInvestigationEvent", "operation"),
                            ("LeakInvestigationReceipt", "caseId")):
            with self.subTest(name=name):
                changed = copy.deepcopy(self.current)
                del changed["components"]["schemas"][name]["properties"][field]
                self.assertEqual(self.compare(current=changed)[0], 1)

    def test_unrelated_receipt_regression_and_future_regression_are_rejected(self):
        changed = copy.deepcopy(self.current)
        del changed["components"]["schemas"]["Receipt"]["properties"]["payloadSha256"]
        code, _, errors = self.compare(current=changed)
        self.assertEqual(code, 1)
        self.assertIn("/other-owner", errors)
        changed = copy.deepcopy(self.current)
        del changed["components"]["schemas"]["IncidentInvestigationReceipt"]["properties"]["actorId"]
        code, output, errors = self.compare(base=self.current, current=changed)
        self.assertEqual(code, 1)
        self.assertNotIn("Owner-approved T62", output)
        self.assertIn("actorId", errors)

    def test_request_exception_rejects_wrong_route_media_and_shape(self):
        route = checker.INCIDENT_INVESTIGATION + "/evidence"
        old = checker.investigation_ref("Evidence")
        new = checker.investigation_ref("IncidentInvestigationEvidenceRequest")
        for path, method, media in ((route, "get", "application/json"),
                                    (checker.LEAK_INVESTIGATION + "/evidence", "post", "application/json"),
                                    (route, "post", "*/*")):
            self.assertFalse(checker.approved_investigation_request_correction(
                self.base, self.current, path, method, media, old, new))
        changed = copy.deepcopy(self.current)
        changed["components"]["schemas"]["IncidentInvestigationEvidenceRequest"]["required"].append("unexpected")
        self.assertFalse(checker.approved_investigation_request_correction(
            self.base, changed, route, "post", "application/json", old, new))
        self.assertEqual(self.compare(current=changed)[0], 1)

    def test_response_exception_does_not_admit_unlisted_route_status_or_media(self):
        old = checker.investigation_ref("View")
        new = checker.investigation_ref("IncidentInvestigationView")
        for route, method, code, media in (
                ("/other-owner", "get", "200", "*/*"),
                (checker.INCIDENT_INVESTIGATION, "post", "200", "*/*"),
                (checker.INCIDENT_INVESTIGATION, "get", "201", "*/*"),
                (checker.INCIDENT_INVESTIGATION, "get", "200", "application/json")):
            self.assertFalse(checker.approved_investigation_response_correction(
                self.base, self.current, route, method, code, media, old, new))


if __name__ == "__main__":
    unittest.main()
