package commands;

import options.IVROption;
//import javax.speech.Engine;
//import javax.speech.Central;

public abstract class IVRCommand {

    private final String number;

    public IVRCommand(final String number) {
        this.number = number;
    }

    public void execute(IVROption option) {
        /*toVoice(*/
            option.getOptions().forEach(System.out::println)
        /*)*/;
    }

}
