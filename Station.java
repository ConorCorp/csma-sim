import java.util.Random;

class Station extends Thread {
    //RUNNING PARAMS
    private static final int t_s = 100; //100 cycle tick
    private static final int t_d = 240; //240 sleep
    private static final int t_p = 120; //Data packet wait t_d * .5
    private static final int t_ifs = 10; //Ack wait
    private static final float p = .3f; // min prob (arbitrary)
    private static final int t_difs = 21;
    private static final int w = 5;
    private Medium medium;
    private int  M = 4; //# of packets for eah station
    private int k = -1, t_tot = 0, t_cw, stNum;

    public Station(Medium medium, int stNum) {
        this.medium = medium;
        this.stNum = stNum;
    }

    //Called on start as well
    //Main process of a Station
    public void run() {        
        while (M > 0) { //Keep generating messages while we have packets to send 22
            sleep(t_d); //1

            float msgProb = getMessageProb();
            while (msgProb < p) {  // 2
                msgProb = getMessageProb();
                t_tot = 0; // 2
                sleep(t_d); //2
            }

            // sleep on a busy medium
            while (medium.isBusy()) { //3 4
                sleep(1);  //5
                t_tot++;    //5
            }

            sleep(t_difs); //6
            t_tot += t_difs; //6

            boolean moveTo16 = false; //for first loop through
            while (!moveTo16) { //hits here on 7, 15
                if ( k < 0 ) {
                    k = 1;  //6
                } else {
                    k *= 2; //15
                    if (k > 16) k = 16; //15
                }                
                t_cw = k*w; //6
                while (t_cw > 0) { //14
                    sleep(1); //7
                    t_cw--; //7
                    t_tot++; //7

                    if (!medium.isBusy()) { // 8 9
                        moveTo16 = true;
                        break; 
                    }

                    do { //10
                        sleep(1);
                        t_tot++;    
                    } while(medium.isBusy()); //11 12

                    sleep(t_difs); //13
                    t_tot += t_difs; //13
                } 
            }
            medium.setBusy(true); //17
            sleep(t_p); //18
            t_tot += t_p;   //18
            sleep(t_ifs);   //19
            t_tot += t_ifs; //19
            medium.setBusy(false);  //20
            sleep(t_difs); //21
            t_tot += t_difs; //21
            M--; // decrementing for 22, checked at beginning
            System.out.println("Station: "+ stNum + " finished a packet with " + t_tot +" ticks");//23
        }
        System.out.println("Finished Staion "+stNum);
        
    }

    //The simulated wait time
    private void sleep(int sleepTime) {
        for (int i = 0; i < (t_s*sleepTime); i++){};
    }

    // This will find the probability that a station
    //has a message to send
    float getMessageProb() {
        Random rand = new Random();
        return rand.nextFloat();
    }

}