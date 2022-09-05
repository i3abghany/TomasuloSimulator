import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Instruction> instructions = new ArrayList<>();
        instructions.add(Instruction.fromString("ADD R1, R2, R3"));
        instructions.add(Instruction.fromString("ADD R5, R1, R6"));
        instructions.add(Instruction.fromString("ADD R3, R5, R6"));
        instructions.add(Instruction.fromString("ADD R4, R5, R6"));
        instructions.add(Instruction.fromString("ADD R5, R1, R6"));
        var cpu = new Cpu(instructions);

        cpu.run();
    }
}
