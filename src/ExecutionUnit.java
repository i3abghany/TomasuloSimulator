public abstract class ExecutionUnit {
    private final int opCycles;
    protected ReservationStationSlot currentSlot = null;

    protected static final int CYCLES_PER_ADD = 4;
    protected static final int CYCLES_PER_MUL = 6;

    public int getOpCycles() {
        return opCycles;
    }

    public int getRemainingCycles() {
        return remainingCycles;
    }

    private int remainingCycles;

    public ExecutionUnit(int _opCycles) {
        opCycles = _opCycles;
    }

    public void dispatch(ReservationStationSlot slot) {
        currentSlot = slot;
        remainingCycles = opCycles;
    }

    public void progress() {
        remainingCycles--;
    }

    public ReservationStationSlot getCurrentSlot() {
        return currentSlot;
    }

    protected boolean executionFinished() {
        return remainingCycles == 0;
    }

    public boolean hasInstruction() {
        return currentSlot != null;
    }

    public abstract int calc();
}
