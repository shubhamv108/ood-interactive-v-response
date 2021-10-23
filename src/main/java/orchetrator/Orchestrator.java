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
    private final Integer maxInputRetries;

    public Orchestrator(IVROption baseOption, int maxRetryAttempts, Long waitTimeout) {
        this.curOption = baseOption;
        this.reader = new ConsoleInputReader();
        this.commandInvoker = new SequenceCommandInvoker(baseOption, maxRetryAttempts);
        this.inputWaitTimeout = waitTimeout;
        this.maxInputRetries = maxRetryAttempts;
    }

    public void execute() throws InterruptedException {
        this.curOption.getOptions().forEach(System.out::println);
        Iterator<String> iterator = this.reader.iterator();

        Integer curInputTimeoutRetries = 0;
        while (curInputTimeoutRetries < this.maxInputRetries) {
            Long nextTimeout = System.currentTimeMillis() + this.inputWaitTimeout;
            while (!iterator.hasNext()) {
                if (System.currentTimeMillis() >= nextTimeout) {
                    if (++curInputTimeoutRetries < this.maxInputRetries)
                        System.out.println("Please press your option");
                    else
                        return;
                    nextTimeout = System.currentTimeMillis() + this.inputWaitTimeout;
                }
            }
            if (iterator.hasNext())
                if (this.commandInvoker.invoke(iterator.next()))
                    curInputTimeoutRetries = 0;
                else if (++curInputTimeoutRetries < this.maxInputRetries)
                    System.out.println("You have not provided the right input. Please try again");
        }
    }

}
