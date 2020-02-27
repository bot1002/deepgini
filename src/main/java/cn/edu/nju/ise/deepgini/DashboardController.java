package cn.edu.nju.ise.deepgini;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class DashboardController {

	// TODO: for later use
    @GetMapping("/dashboard")
    public String hello(Model model){
    	return "dashboard";
    }


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file,@RequestParam String type){
        String filePath ;
        if(type.equals("model")){
            filePath =  "src/main/resources/modelFiles/" +file.getOriginalFilename();
        }else if(type.equals("testSet")){
            filePath =  "src/main/resources/testSetFiles/" +file.getOriginalFilename();
        }else {
            filePath = "./" +file.getOriginalFilename();
        }
        File desFile = new File(filePath);
        try {
            if(!desFile.exists()){
                desFile.createNewFile();
            }
            file.transferTo(desFile.getAbsoluteFile());
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getOriginalFilename());
        return "hello";
    }

}
