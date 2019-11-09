package cn.edu.nju.ise.deepgini;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	// TODO: for later use
    @GetMapping("/dashboard")
    public String hello(Model model){
    	return "dashboard";
    }
}
