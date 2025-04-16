package za.co.wethinkcode.flow;

public class Recorder {
    public void logTest(List<String> inputs, List<String> outputs, List<String> errors, List<String> asserts) {
        System.out.println("Inputs: " + inputs);
        System.out.println("Outputs: " + outputs);
        System.out.println("Errors: " + errors);
        System.out.println("Asserts: " + asserts);
    }

}
