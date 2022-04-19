package helpers;

public class OutputFormatter {
    public static String buildXMLFormat(String text) {
        String xmlElement = text;
        xmlElement = xmlElement.replaceAll(":", "=\"")
                .replaceAll(",", "\"")
                .replaceAll("null", "\"null");
        xmlElement = "<response " + xmlElement + "\"/>";
        return xmlElement;
    }
}
