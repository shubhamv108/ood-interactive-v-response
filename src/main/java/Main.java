public class Main {
    public static void main(String[] args) {

        IVRState baseState = IVRState.builder()
                .withOptionText("")
                .build();
        IVRState base1 = IVRState.builder()
                .withOptionText("Please press 1 for Prepaid connection")
                .withBaseState(baseState)
                .build();
        IVRState base2 = IVRState.builder()
                .withOptionText("Please press 2 for Postpaid connection")
                .withBaseState(baseState)
                .build();
        IVRState base3 = IVRState.builder()
                .withOptionText("Please press 3 for Broadband")
                .withBaseState(baseState)
                .build();
        baseState.setNextOption(1, base1);
        baseState.setNextOption(2, base2);
        baseState.setNextOption(3, base3);


        Orchestrator orchestrator = new Orchestrator(baseState);
        orchestrator.execute();
    }

}
