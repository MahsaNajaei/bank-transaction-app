package configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import entities.database.CustomerDeposit;
import helpers.ServerLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Map;

public class ConfigHandler {

    private File configFile;
    private ObjectMapper configPropertiesMapper;
    private static ConfigHandler configHandler = new ConfigHandler("ir.dotin.server\\src\\core.json");

    private ConfigHandler(String filePath) {
        configFile = new File(filePath);
        registerConfigPropertiesMapper();
    }

    public ConfigProperties loadConfigs() throws IOException {
        return configPropertiesMapper.readValue(configFile, ConfigProperties.class);
    }

    public  void updateConfigFile(Map<Integer, CustomerDeposit> databaseInfo) {

        try {
            JSONObject configFileContent = (JSONObject) new JSONParser().parse(new FileReader(configFile));
            JSONArray depositsArray = (JSONArray) configFileContent.get("deposits");

            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberFormat.setGroupingUsed(true);

            for (int i = 0; i < depositsArray.size(); i++) {
                JSONObject depositInfo = (JSONObject) depositsArray.get(i);
                int depositId = Integer.parseInt((String) depositInfo.get("id"));
                CustomerDeposit customerDeposit = databaseInfo.get(depositId);
                depositInfo.replace("initialBalance", numberFormat.format(customerDeposit.getDepositInfo().getBalance()));
            }

            configPropertiesMapper.writerWithDefaultPrettyPrinter().writeValue(configFile, configFileContent);

        } catch (FileNotFoundException e) {
            ServerLogger.getLogger().severe(e.getMessage());
        } catch (IOException e) {
            ServerLogger.getLogger().severe(e.getMessage());
        } catch (ParseException e) {
            ServerLogger.getLogger().severe(e.getMessage());
        }
    }

    public static ConfigHandler getConfigHandler() {
        return configHandler;
    }

    private void registerConfigPropertiesMapper() {
        configPropertiesMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(ConfigProperties.class, new ConfigPropertiesDeserializer());
        configPropertiesMapper.registerModule(simpleModule);
    }

}
