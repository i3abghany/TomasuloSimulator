public class MulExecutionUnit extends ExecutionUnit {

    public MulExecutionUnit(Cpu cpu) {
        super(CYCLES_PER_MUL);
    }

    @Override
    public int calc() {
        if (!super.executionFinished())
            throw new RuntimeException("MUL instruction didn't finish execution yet.");
        int op1Value = currentSlot.getSrc1().getValue();
        int op2Value = currentSlot.getSrc2().getValue();
        currentSlot = null;
        return op1Value * op2Value;
    }
}
