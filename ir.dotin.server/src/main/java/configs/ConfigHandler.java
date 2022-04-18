package configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    public ConfigProperties loadConfigs() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(ConfigProperties.class, new ConfigPropertiesDeserializer());
        objectMapper.registerModule(simpleModule);

        File configFile = new File("ir.dotin.server\\src\\core.json");

        return objectMapper.readValue(configFile, ConfigProperties.class);
    }

}
