
class Csma_sim {
    //CONFIGURATION PARAMS
    //see Station.java for Running Params
    public static int NUM_STATIONS = 4;
    private static int t_s = 100; //100 cycle tick
    private static int t_d = 240; //240 sleep
    private static int t_p = 120; //nbetween t_d .3 and td .6
    private static int t_ifs = 10; //Ack wait
    private static float p = .3f; // min prob (arbitrary)
    private static int t_difs = 21; //20 < x < 40
    private static int w = 5; //congestion between ts and 2 * num station
    private static int M = 4; //packets

    //Arg 1 = number of stations
    //Arg 2 = P
    //Arg 3 = t_p
    //Arg 4 = M
    public static void main(String args[]) {
        if (args.length > 0) NUM_STATIONS = Integer.parseInt(args[0]);
        if (args.length > 1) p = Float.parseFloat(args[1]);
        if (args.length > 2) t_p = Integer.parseInt(args[2]);
        if (args.length > 3) M = Integer.parseInt(args[3]);

        //init Medium
        Medium medium = new Medium();

        //init stations
        Station[] stns = new Station[NUM_STATIONS];
        for (int i = 0; i < NUM_STATIONS; i++) {
            stns[i] = new Station(medium, i, t_s, t_d, t_p, t_ifs, p, t_difs, w, M);
            stns[i].start();
        }
    }
}