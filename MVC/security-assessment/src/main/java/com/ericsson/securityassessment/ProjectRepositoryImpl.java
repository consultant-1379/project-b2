package com.ericsson.securityassessment;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    @PersistenceContext
    protected EntityManager entityManager;


    @Override
    public List<Issue> getIssuesByProjectID(long projectId) {
        String jpql = "select i from Issue i WHERE i.project.projectId = ?0";
        TypedQuery<Issue> query = entityManager.createQuery(jpql, Issue.class);
        query.setParameter(0,projectId);
        return  query.getResultList();
    }

    @Override
    public List<Issue> getIssuesByProjectKey(String key) {
        Project p = this.getIssuelessProjectByKey(key);
        return getIssuesByProjectID(p.getId());

    }

    @Override
    public Project getProjectByKey(String key){
        Project project = getIssuelessProjectByKey(key);
        if(project != null){ project.addIssues(getIssuesByProjectKey(key));}
        return project;
    }

    @Override
    public Project getIssuelessProjectByKey(String key){
        Project project = null;
        try {
            String jpql = "select p from Project p WHERE p.projectKey = ?0";
            TypedQuery<Project> query = entityManager.createQuery(jpql, Project.class);
            query.setParameter(0,key);
            project = query.getSingleResult();
        }catch(NoResultException e){
            Logger logger = Logger.getLogger(ProjectRepositoryImpl.class.getName());
            logger.log(Level.INFO, "There is no project key found in database");
        }
        return project;
    }

    @Transactional
    @Override
    public void deleteProject(String key){
        Project project = getIssuelessProjectByKey(key);
        String jpql = "delete from Issue i Where i.project.projectId = ?0";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(0,project.getId());
        query.executeUpdate();

        jpql = "delete from Project p Where p.projectKey = ?0";
        query = entityManager.createQuery(jpql);
        query.setParameter(0,key);
        query.executeUpdate();

    }

    @Transactional
    @Override
    public long insertProject(Project project){
        entityManager.persist(project);
        entityManager.flush();
        insertIssues(project);
        return project.getId();
    }

    @Transactional
    @Override
    public void insertIssues(Project project){
        List<Issue> issues = project.getProjectIssues();
        for(int i = 0; i < issues.size(); i++){
            entityManager.persist(issues.get(i));
            entityManager.flush();
        }
    }

}
