package helpers;

import java.io.FileWriter;
import java.io.IOException;

public class TerminalDataRecorder implements Recorder {
    FileWriter fileWriter;

    public TerminalDataRecorder(String filePath) throws IOException {
        fileWriter = new FileWriter(filePath);
    }

    @Override
    public void record(String data) throws IOException {
        fileWriter.write(data + "\n");
        fileWriter.flush();
    }
}
