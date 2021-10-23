package readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public abstract class InputReader implements Iterable<String> {

    private final BufferedReader bufferedReader;

    protected InputReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String acceptNext() {
        String next = null;
        try {
            next = InputReader.this.bufferedReader.readLine();
            if (next != null)
                next = next.trim();
            if (next == null || next.isBlank())
                return this.acceptNext();
        } catch (IOException e) {
            System.out.println("Invalid input");
        }
        return next;
    }

    @Override
    public Iterator<String> iterator() {
        return new InputReaderIterator();
    }

    public class InputReaderIterator implements Iterator<String> {
        @Override
        public boolean hasNext() {
            try {
                return InputReader.this.bufferedReader.ready();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public String next() {
            return InputReader.this.acceptNext();
        }
    }
}
