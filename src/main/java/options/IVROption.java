package options;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class IVROption {

    private final String optionText;
    private IVROption parent;

    private final Map<Integer, IVROption> nextOptions;

    public IVROption(String optionText, Map<Integer, IVROption> nextOptions) {
        this.optionText = optionText;
        this.nextOptions = nextOptions;
        this.nextOptions.forEach((k, v) -> {
            if (k != 8 && k != 9)
                v.setParent(this);
        });
    }

    private void setParent(IVROption parent) {
        this.parent = parent;
        this.nextOptions.put(8, parent);
    }

    public IVROption getNext(Integer input) {
        IVROption next = this.nextOptions.get(input);
        if (next == null)
            throw new IllegalArgumentException("You have not provided the right input. Please try again");
        return next;
    }

    public void setNextOption(int number, IVROption state) {
        this.nextOptions.putIfAbsent(number, state);
        state.setParent(this);
    }

    public String getOptionText() {
        return optionText;
    }

    public List<String> getOptions() {
        List<String> options = this.nextOptions.entrySet()
                .stream()
                .map(entry -> {
                    if (entry.getValue() == this.parent)
                        if (entry.getKey() == 8)
                            return "Press 8 to go back to the previous menu";
                    return entry.getValue().getOptionText();
                })
                .filter(Objects::nonNull)
                .filter(e -> !e.isBlank())
                .collect(Collectors.toList());
        if (this.nextOptions.containsKey(9))
            options.add("Press 9 to go back to the main menu");
        options.add("Please disconnect to end this call");
        return options;
    }

    public static IVRStateBuilder builder() {
        return new IVRStateBuilder();
    }

    public static class IVRStateBuilder {
        private String optionText;
        private final Map<Integer, IVROption> ivrSubStatesPrototypes= new TreeMap<>();

        public IVRStateBuilder withOptionText(String optionText) {
            this.optionText = optionText;
            return this;
        }

        public IVRStateBuilder withNextOption(int number, IVROption state) {
            this.ivrSubStatesPrototypes.putIfAbsent(number, state);
            return this;
        }

        public IVRStateBuilder withBaseState(IVROption baseState) {
            this.ivrSubStatesPrototypes.put(9, baseState);
            return this;
        }

        public IVROption build() {
            return new IVROption(optionText, ivrSubStatesPrototypes);
        }
    }

}
