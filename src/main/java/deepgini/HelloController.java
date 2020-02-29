package deepgini;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
public class HelloController {
  @GetMapping("/hello")
  public ArrayList<Integer> hello(@RequestParam(value = "name", defaultValue = "World") String name) {

    ArrayList<Integer> ret = new ArrayList<>();

    try {
      Process process = Runtime.getRuntime().exec("pwd");
      InputStreamReader iStreamReader = new InputStreamReader(process.getInputStream());
      BufferedReader bufferedReader = new BufferedReader(iStreamReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      Process process = Runtime.getRuntime().exec("python python/hello.py");
      InputStreamReader iStreamReader = new InputStreamReader(process.getInputStream());
      BufferedReader bufferedReader = new BufferedReader(iStreamReader);

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
        ret.add(Integer.parseInt(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return ret;
  }
}
