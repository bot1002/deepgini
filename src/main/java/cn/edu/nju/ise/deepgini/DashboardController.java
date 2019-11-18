package cn.edu.nju.ise.deepgini;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DashboardController {

	// TODO: for later use
    @GetMapping("/dashboard")
    public String hello(Model model){
    	return "dashboard";
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file){
        System.out.println(file.getOriginalFilename());
        return "hello";
    }
}
