package com.ericsson.securityassessment;



import javax.persistence.*;
import java.util.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long projectId = null;
    @Column(unique = true)
    private String projectKey;

    @OneToMany(mappedBy = "project")
    private List<Issue> issues;

    public Project() {this.issues = new ArrayList<>();}

    public Project(String projectKey) {
        this.projectKey = projectKey;
        this.issues = new ArrayList<>();
    }

    public void addIssue(OWSAPTag tag, Severity severity){
        Issue issue = new Issue(tag, severity, this);
        this.issues.add(issue);
    }

    public void addIssuesByString(List<String> issuesList){
        OWSAPTag tag;
        Severity sev;
        for (int i = 0; i< issuesList.size(); i+=2){
            if(issuesList.get(i).equals("owasp-a10")){tag = OWSAPTag.valueOf("A10");}
            else{tag = OWSAPTag.valueOf(issuesList.get(i).substring(6).toUpperCase());}
            sev = Severity.valueOf(issuesList.get(i+1));
            addIssue(tag, sev);
        }
    }

    public void addIssues(List<Issue> issuesList) {
        this.issues = issuesList;
    }

    public List<Issue> getProjectIssues() {
        return issues;
    }

    public Long getId() {
        return projectId;
    }


    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

}
