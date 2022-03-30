package arep;

public class MathResponse {

    private String operation;
    private String input;
    private String output;

    public MathResponse(String operation, String input, String output) {
        this.operation = operation;
        this.input = input;
        this.output = output;
    }

    public String getOperation() {
        return operation;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
