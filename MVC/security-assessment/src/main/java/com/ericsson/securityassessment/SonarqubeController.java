package com.ericsson.securityassessment;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class SonarqubeController {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${sonarqube.url}")
    private String sonarqubeURL;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping(value = "/search")
    @PostMapping(value = "/search")
    public String generateReport(@RequestParam(value = "name") String name, Model model) {
        Project project;
        if(doesExist(name)){
            project = projectRepository.getProjectByKey(name);
        }
        else{
            project = new Project(name);
            project.addIssuesByString(createData(name));
            projectRepository.insertProject(project);
        }
        Map<Severity, List<Integer>> mappedIssues = mapIssues(project.getProjectIssues());
        model.addAttribute("issues", mappedIssues);
        model.addAttribute("name", project.getProjectKey());
        return "result";
    }

    public static Map<Severity, List<Integer>> mapIssues(List<Issue> issues){
        Map<Severity, List<Integer>> issuesMap = new EnumMap<>(Severity.class);
        for(Severity sev: Severity.values()){
            List<Integer> startingValues = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            issuesMap.put(sev, startingValues);
        }
        for (int i = 0; i < issues.size(); i++){
            Severity key = issues.get(i).getSeverity();
            List<Integer> valueList = issuesMap.get(key);
            int pos = issues.get(i).getOwsapTag().ordinal();
            valueList.set(pos, valueList.get(pos) + 1);
            issuesMap.put(key,valueList);
        }
        return issuesMap;
    }

    public boolean doesExist(String projectKey) {
        Project p = projectRepository.getProjectByKey(projectKey);
        return (p != null);
    }

    public List<String> createData(String name) {
        String urlString = "http://"+sonarqubeURL+":9000/api/issues/search?componentKeys=" + name + "&owaspTop10=a1,a2,a3,a4,a5,a6,a7,a8,a9,a10";
        String json = restTemplate.getForObject(urlString, String.class);
        try {
            org.json.JSONObject jsonObject = new JSONObject(json);
            return JSONToReport.returnReport(jsonObject);
        } catch (JSONException e) {
            Logger logger = Logger.getLogger(SonarqubeController.class.getName());
            logger.log(Level.SEVERE,"Could not retrieve JSON from URL, returning empty list instead");
            return new ArrayList<>();
        }
    }
}
