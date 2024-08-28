package com.ericsson.securityassessment;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectRepository {

    List<Issue> getIssuesByProjectID(long projectId);

    List<Issue> getIssuesByProjectKey(String key);

    Project getIssuelessProjectByKey(String key);

    Project getProjectByKey(String key);

    @Transactional
    void deleteProject(String key);

    @Transactional
    long insertProject(Project project);

    @Transactional
    void insertIssues(Project project);
}
