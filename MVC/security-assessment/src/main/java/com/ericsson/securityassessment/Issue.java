package com.ericsson.securityassessment;

import javax.persistence.*;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId = null;

    @ManyToOne
    @JoinColumn(name="project_id",nullable = false)
    private Project project;

    public Severity getSeverity() {
        return severity;
    }

    public OWSAPTag getOwsapTag() {
        return owsapTag;
    }

    private Severity severity;
    private OWSAPTag owsapTag;

    public Issue() {
    }

    public Issue(OWSAPTag tag, Severity severity, Project project){
        this.owsapTag = tag;
        this.severity = severity;
        this.project = project;
    }
}
