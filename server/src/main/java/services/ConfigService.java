package services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Environment;

import java.io.*;

/**
 * Created by manasb on 27-11-2016.
 */
public class ConfigService {

    private JsonObject fileContents;
    private static ConfigService instance;

    private ConfigService() throws Exception {
        read();
    }

    public static synchronized ConfigService getInstance() throws Exception {
        if (instance == null) {
            instance = new ConfigService();
        }

        return instance;
    }

    private void read() throws Exception {
        final String configFilePath = "config.json";
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFilePath);
        if (inputStream == null) {
            throw new FileNotFoundException(String.format("Could not find config file [%s]", configFilePath));
        }

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = fileReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            fileContents = convertStringToJsonObject(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Environment getEnvironment() {
        return Environment.valueOf(fileContents.get("environment").getAsString());
    }

    public int getPort() {
        return fileContents.get("server_port").getAsInt();
    }

    public String getWebSocketEndpoint() {
        return fileContents.get("websocket_endpoint").getAsString();
    }

    private JsonObject convertStringToJsonObject(String jsonString) {
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(jsonString).getAsJsonObject();
    }
}
