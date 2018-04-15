
class Csma_sim {
    public static final int NUM_STATIONS = 8;

    public static void main(String args[]) {
        //init Medium
        Medium medium = new Medium();

        //init stations
        Station[] stns = new Station[NUM_STATIONS];
        for (int i = 0; i < 8; i++) {
            stns[i] = new Station(medium, i);
            stns[i].start();
        }
    }
}