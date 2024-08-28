package com.ericsson.securityassessment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JSONToReport {

    private JSONToReport(){}

    public static List<String> returnReport(JSONObject jsonOutput) {
        List<String> results = new ArrayList<>();
        List<String> owaspCats = Arrays.asList("owasp-a1", "owasp-a2", "owasp-a3", "owasp-a4", "owasp-a5", "owasp-a6", "owasp-a7", "owasp-a8", "owasp-a9", "owasp-a10");
        try {
            JSONArray issuesJSON = jsonOutput.getJSONArray("issues");
            for (int i = 0; i < issuesJSON.length(); i++) {
                JSONObject issue = (JSONObject) issuesJSON.get(i);
                JSONArray tagsArray = issue.getJSONArray("tags");
                for (int j = 0; j < tagsArray.length(); j++) {
                    if (owaspCats.contains(tagsArray.get(j).toString())) {
                        results.add(tagsArray.get(j).toString());
                        if (issue.has("severity")) { results.add(issue.get("severity").toString()); }
                        else {results.add("SECURITY_HOTSPOT"); }
                    }
                }
            }
        } catch (JSONException e) {
            Logger logger = Logger.getLogger(JSONToReport.class.getName());
            logger.log(Level.SEVERE, "This is not a JSON input, check the WEBAPI connection");

        }
        return results;
    }
}
