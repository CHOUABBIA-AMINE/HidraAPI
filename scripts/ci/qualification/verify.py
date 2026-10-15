"""Synthetic-only CI evidence policy negative cases; no production gate mutation."""
import unittest

REQUIRED = frozenset({"oracle", "pg1", "pg2"})

def acceptable(evidence):
    if evidence.get("cancelled") or evidence.get("stale"):
        return False
    jobs = evidence.get("jobs", {})
    if set(jobs) != REQUIRED:
        return False
    if len(evidence.get("artifact_ids", [])) != len(set(evidence.get("artifact_ids", []))):
        return False
    return all(v.get("conclusion") == "success" and v.get("tests", 0) > 0 and v.get("skipped", 0) == 0 and v.get("sha") == evidence.get("sha") for v in jobs.values())

class Qualification(unittest.TestCase):
    def test_negative_cases(self):
        base = {"sha":"synthetic", "artifact_ids":["a","b","c"], "jobs":{k:{"sha":"synthetic","conclusion":"success","tests":1,"skipped":0} for k in REQUIRED}}
        self.assertTrue(acceptable(base))
        for key, value in [("cancelled",True),("stale",True)]:
            self.assertFalse(acceptable({**base,key:value}))
        self.assertFalse(acceptable({**base,"artifact_ids":["a","a","c"]}))
        self.assertFalse(acceptable({**base,"jobs":{k:v for k,v in base["jobs"].items() if k != "pg1"}}))
        for k in REQUIRED:
            for field, value in [("conclusion","failure"),("conclusion","cancelled"),("tests",0),("skipped",1),("sha","other")]:
                jobs = {name:dict(data) for name,data in base["jobs"].items()}
                jobs[k][field]=value
                self.assertFalse(acceptable({**base,"jobs":jobs}))

if __name__ == "__main__":
    unittest.main()
