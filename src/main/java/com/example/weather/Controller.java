package com.example.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONObject;

public class Controller {

    @FXML
    private Button getData;

    @FXML
    private TextField name;

    @FXML
    private Text pressure;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserName = name.getText().trim();
            if(!getUserName.isEmpty()) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserName + "&appid=dd9a00b60b5148bbe64485da7c7c487c&units=metric");

                if (!output.isEmpty()) {
                    if (!output.equals("入力ミス")) {
                        JSONObject obj = new JSONObject(output);
                        temp_info.setText("気温：" + obj.getJSONObject("main").getDouble("temp"));
                        temp_info.setTextAlignment(TextAlignment.LEFT);
                        temp_info.setFill(Color.WHITE);
                        temp_feels.setText("体感気温：" + obj.getJSONObject("main").getDouble("feels_like"));
                        temp_max.setText("最高気温：" + obj.getJSONObject("main").getDouble("temp_max"));
                        temp_min.setText("最低気温：" + obj.getJSONObject("main").getDouble("temp_min"));
                        pressure.setText("気圧：" + obj.getJSONObject("main").getDouble("pressure"));
                    }
                    else {
                        temp_info.setText(output);
                        temp_info.setTextAlignment(TextAlignment.CENTER);
                        temp_info.setFill(Color.RED);
                        temp_feels.setText("");
                        temp_max.setText("");
                        temp_min.setText("");
                        pressure.setText("");
                    }
                }
            }
        });
    }

    private static String getUrlContent(String urlAddress) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("入力ミス");
            return "入力ミス";
        }
        return content.toString();
    }
}