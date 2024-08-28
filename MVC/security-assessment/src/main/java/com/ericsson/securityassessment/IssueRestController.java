package com.ericsson.securityassessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IssueRestController {

    @Autowired
    private ProjectRepository repo;


    @PostMapping("/")
    public String getTop10(Model model) {
        return "result";
}

    @GetMapping("/")
    public String enterId(){
        return "request";
    }

}

