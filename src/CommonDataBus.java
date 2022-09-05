import java.util.ArrayList;

public class CommonDataBus {
    private final RegisterFile connectedRegisterFile;
    private final ArrayList<ReservationStation> reservationStations;

    public CommonDataBus(RegisterFile _registerFile, ArrayList<ReservationStation> _reservationStationList) {
        connectedRegisterFile = _registerFile;
        reservationStations = _reservationStationList;
    }

    public void broadcastValue(String tag, int value) {
        for (var station: reservationStations)
            station.broadcastValue(tag, value);
    }

    public void writeBack(String tag, int value) {
        connectedRegisterFile.broadcastValue(tag, value);
    }
}
