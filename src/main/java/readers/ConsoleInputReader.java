package readers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleInputReader extends InputReader {

    public ConsoleInputReader() {
        super(new BufferedReader(new InputStreamReader(System.in)));
    }
}
