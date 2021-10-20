import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public abstract class InputReader implements Iterable<String> {

    private final BufferedReader bufferedReader;

    protected InputReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public Iterator<String> iterator() {
        return new InputReaderIterator();
    }

    private class InputReaderIterator implements Iterator<String> {

        String next;

        @Override
        public boolean hasNext() {
            try {
                this.next = InputReader.this.bufferedReader.readLine();
                if (this.next != null)
                    this.next = this.next.trim();
//                if (this.next == null || this.next.isEmpty())
//                    return this.hasNext();
            } catch (IOException e) {
                System.out.println("Invalid input");
            }
            return next != null && !next.isEmpty();
        }

        @Override
        public String next() {
            String result = next;
            next = null;
            return result;
        }
    }
}
