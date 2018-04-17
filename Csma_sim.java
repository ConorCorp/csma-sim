
class Csma_sim {
    //CONFIGURATION PARAMS
    //see Station.java for Running Params
    public static final int NUM_STATIONS = 8;

    public static void main(String args[]) {
        //init Medium
        Medium medium = new Medium();

        //init stations
        Station[] stns = new Station[NUM_STATIONS];
        for (int i = 0; i < NUM_STATIONS; i++) {
            stns[i] = new Station(medium, i);
            stns[i].start();
        }
    }
}