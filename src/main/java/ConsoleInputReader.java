import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleInputReader extends InputReader {

    protected ConsoleInputReader() {
        super(new BufferedReader(new InputStreamReader(System.in)));
    }
}
