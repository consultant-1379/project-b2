package com.ericsson.securityassessment;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SecurityAssessmentApplicationTests {
	private List<String> demoData = Arrays.asList("owasp-a1", "SECURITY_HOTSPOT", "owasp-a2", "BLOCKER", "owasp-a3", "SECURITY_HOTSPOT", "owasp-a3", "SECURITY_HOTSPOT", "owasp-a2", "BLOCKER", "owasp-a6", "BLOCKER", "owasp-a3", "MINOR");
	private String projectName = "sampleName";
	private Project testProject;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private RestTemplate restTemplate;

	@BeforeEach
	public void setup(){
		testProject = new Project();
		testProject.setProjectKey(projectName);
		if(projectRepository.getProjectByKey(projectName) != null){
			projectRepository.deleteProject(projectName);
		}
		testProject.addIssuesByString(demoData);
		projectRepository.insertProject(testProject);
	}

	@Test
	public void testSpringApplicationRun()
	{
		SecurityAssessmentApplication.main(new String[]{
				"--spring.main.web-environment=false",
				"--spring.autoconfigure.exclude=blahblahblah",
				// Override any other environment properties according to your needs
		});
	}


	@Test
	 void testConnection(){
		assertThat(projectRepository).isNotNull();
	}

	@Test
	void reportCreated() throws JSONException {
		String json = restTemplate.getForObject("http://localhost:9000/api/issues/search?componentKeys=attempt2&owaspTop10=a1,a2,a3,a4,a5,a6,a7,a8,a9,a10", String.class);
		JSONObject jsonObject = new JSONObject(json);
		List<String> results = JSONToReport.returnReport(jsonObject);
		List<String> expected = new ArrayList<>();
		//temporary since the correct repo data isn't used
		assertEquals(expected, results);
	}

	@Test
	void addProjectDataToDB(){
		Project newProject = new Project("");
		newProject.setProjectKey("Test Project");
		//newProject.setProjectKey(projectName);
		if(projectRepository.getProjectByKey(newProject.getProjectKey()) != null){
			projectRepository.deleteProject(newProject.getProjectKey());
		}

		newProject.addIssuesByString(demoData);
		long id = projectRepository.insertProject(newProject);
		assertEquals(newProject.getId(),id);
	}

	@Test
	void testIssueMapping(){
		Map<Severity, List<Integer>> mappedIssues = SonarqubeController.mapIssues(testProject.getProjectIssues());
		assertEquals(2, mappedIssues.get(Severity.SECURITY_HOTSPOT).get(2));
		System.out.println(mappedIssues);
	}

	@Test
	void gatherDataFromDB(){
		Project project = projectRepository.getProjectByKey(projectName);
		assertEquals(project.getProjectKey(), projectName);
		List<Issue> issues = project.getProjectIssues();
	}

	@Test
	void getNotExistentProjectKey(){
		String key = "asdfgasdgasaffasf";
		Project p = projectRepository.getIssuelessProjectByKey(key);
		assertThat(p).isNull();
	}
}
