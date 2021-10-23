import options.IVROption;
import orchetrator.Orchestrator;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        IVROption baseState = IVROption.builder()
                .withOptionText("")
                .build();
        IVROption base1 = IVROption.builder()
                .withOptionText("Please press 1 for Prepaid connection")
                .withBaseState(baseState)
                .build();
        IVROption base2 = IVROption.builder()
                .withOptionText("Please press 2 for Postpaid connection")
                .withBaseState(baseState)
                .build();
        IVROption base3 = IVROption.builder()
                .withOptionText("Please press 3 for Broadband")
                .withBaseState(baseState)
                .build();
        baseState.setNextOption(1, base1);
        baseState.setNextOption(2, base2);
        baseState.setNextOption(3, base3);


        Orchestrator orchestrator = new Orchestrator(baseState, 3, 10000L);
        orchestrator.execute();
    }

}
