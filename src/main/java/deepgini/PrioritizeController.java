package deepgini;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@RestController
public class PrioritizeController {

  @GetMapping("/prioritize")
  public String prioritize(@RequestParam(value = "useDefault", defaultValue = "true") String useDefault,
                           @RequestParam(value = "dataset", defaultValue = "mnist") String dataset,
                           @RequestParam(value = "metric", defaultValue = "nac") String metric) {
    ArrayList<CAM> valueOfCAMs = new ArrayList<>();
    CAM cam = null;

    // Step 1: 在指定的数据集上训练模型，若已有模型文件，则无需再次训练
    if (!new File("./python/model/model" + dataset + ".hdf5").exists()) {
      try {
        Process process = Runtime.getRuntime().exec(ServerConfig.pyInterpreter + " python/model_mnist.py");
        InputStreamReader iStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(iStreamReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          System.out.println(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Step 2: 根据metric参数，dispatch到不同算法，以得到该模型认为的Top-5易错测试用例
    if (!new File("./python/output_mnist").isDirectory()) {
      try {
        Process process = Runtime.getRuntime().exec(
            ServerConfig.pyInterpreter + " python/mnist_exp.py --nac");
        // InputStreamReader iStreamReader = new InputStreamReader(process.getInputStream());
        // BufferedReader bufferedReader = new BufferedReader(iStreamReader);
        // String line;
        // while ((line = bufferedReader.readLine()) != null) {
        //   System.out.println(line);
        // }

        // 或许python将结果写入文件，java去读文件是更好的选择
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Step 3: 根据最后一层score计算CAM
    try {
      Process process = Runtime.getRuntime().exec(
           ServerConfig.pyInterpreter + " python/statistic.py --" + dataset);
      // InputStreamReader iStreamReader = new InputStreamReader(process.getErrorStream());
      InputStreamReader iStreamReader = new InputStreamReader(process.getInputStream());
      BufferedReader bufferedReader = new BufferedReader(iStreamReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // System.out.println(line);
        cam = new CAM(dataset, Double.parseDouble(line));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Step 3: 将结果以json格式返回
    Gson gson = new Gson();
    return gson.toJson(cam);
  }
}
