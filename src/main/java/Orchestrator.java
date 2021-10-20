import java.util.Iterator;

public class Orchestrator {

    private IVRState baseState;
    private InputReader reader;
    private SequenceCommandInvoker commandInvoker;

    public Orchestrator(IVRState baseState) {
        this.baseState = baseState;
        this.reader = new ConsoleInputReader();
        this.commandInvoker = new SequenceCommandInvoker(baseState);
    }

    public void execute() {
        System.out.println(baseState.getOptions());
        Iterator<String> iterator = this.reader.iterator();
        while (iterator.hasNext()) {
            this.commandInvoker.invoke(iterator.next());
        }
    }

}