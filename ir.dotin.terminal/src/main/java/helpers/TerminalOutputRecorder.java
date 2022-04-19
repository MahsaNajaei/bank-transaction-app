package helpers;

import java.io.FileWriter;
import java.io.IOException;

public class TerminalOutputRecorder implements Recorder {
    FileWriter fileWriter;

    public TerminalOutputRecorder(String filePath) throws IOException {
        fileWriter = new FileWriter(filePath);
    }

    @Override
    public void record(String data) throws IOException {
        fileWriter.write(OutputFormatter.buildXMLFormat(data) + "\n");
        fileWriter.flush();
    }
}
