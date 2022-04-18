package configs;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import entities.database.CustomerDeposit;
import entities.database.DatabaseInfoBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class ConfigPropertiesDeserializer extends StdDeserializer<ConfigProperties> {

    public ConfigPropertiesDeserializer() {
        this(null);
    }

    public ConfigPropertiesDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ConfigProperties deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        int port = rootNode.get("port").asInt();
        String logPath = rootNode.get("outLog").asText();

        ArrayNode depositsInfo = (ArrayNode) rootNode.get("deposits");
        Map<Integer, CustomerDeposit> databaseInfoByDepositId = new HashMap<>();
        for (int i = 0; i < depositsInfo.size(); i++) {
            JsonNode customerDepositNode = depositsInfo.get(i);
            String fullName = customerDepositNode.get("customer").asText();
            int depositId = customerDepositNode.get("id").asInt();

            String balanceString = customerDepositNode.get("initialBalance").textValue().replaceAll(",", "");
            BigDecimal balance = new BigDecimal(balanceString);

            String upperBoundStr = customerDepositNode.get("upperBound").asText().replaceAll(",", "");
            BigDecimal upperBound = new BigDecimal(upperBoundStr);

            CustomerDeposit customerDeposit = new DatabaseInfoBuilder(fullName, depositId, balance, upperBound).build();
            databaseInfoByDepositId.put(depositId, customerDeposit);
        }

        return new ConfigProperties(port, databaseInfoByDepositId, logPath);
    }
}
