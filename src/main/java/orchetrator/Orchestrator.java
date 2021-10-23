package orchetrator;

import options.IVROption;
import commands.invokers.SequenceCommandInvoker;
import readers.ConsoleInputReader;
import readers.InputReader;

import java.util.Iterator;

public class Orchestrator {

    private IVROption curOption;
    private final InputReader reader;
    private final SequenceCommandInvoker commandInvoker;
    private final Long inputWaitTimeout;
    private final Integer maxInputTimeoutRetries;

    public Orchestrator(IVROption baseOption, int maxRetryAttempts, Long waitTimeout) {
        this.curOption = baseOption;
        this.reader = new ConsoleInputReader();
        this.commandInvoker = new SequenceCommandInvoker(baseOption, maxRetryAttempts);
        this.inputWaitTimeout = waitTimeout;
        this.maxInputTimeoutRetries = maxRetryAttempts;
    }

    public void execute() throws InterruptedException {
        this.curOption.getOptions().forEach(System.out::println);
        Iterator<String> iterator = this.reader.iterator();

        Integer curInputTimeoutRetries = 0;
        while (curInputTimeoutRetries < this.maxInputTimeoutRetries) {
            Long nextTimeout = System.currentTimeMillis() + this.inputWaitTimeout;
            while (!iterator.hasNext()) {
                if (System.currentTimeMillis() >= nextTimeout) {
                    if (++curInputTimeoutRetries < this.maxInputTimeoutRetries)
                        System.out.println("Please press your option");
                    else {
                        System.out.println("Ending call");
                        return;
                    }
                    nextTimeout = System.currentTimeMillis() + this.inputWaitTimeout;
                }
            }
            if (iterator.hasNext()) {
                curInputTimeoutRetries = 0;
                this.commandInvoker.invoke(iterator.next());
            }
        }
    }

}
