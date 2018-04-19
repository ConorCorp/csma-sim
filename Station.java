import java.util.Random;

class Station extends Thread {
    //RUNNING PARAMS
    private int t_s; //100 cycle tick
    private int t_d; //240 sleep
    private int t_p; //Data packet wait t_d * .5
    private int t_ifs; //Ack wait
    private float p; // min prob (arbitrary)
    private int t_difs;
    private int w;
    private Medium medium;
    private int  M; //# of packets for eah station
    private int k = -1, t_tot = 0, t_cw, stNum, t_tot_all=0;

    public Station(Medium medium, int stNum, int t_s, int t_d, int t_p, int t_ifs, float p, int t_difs, int w, int M) {
        this.t_s = t_s; //100 cycle tick
        this.t_d = t_d; //240 sleep
        this.t_p = t_p; //Data packet wait t_d * .5
        this.t_ifs = t_ifs; //Ack wait
        this.p = p; // min prob (arbitrary)
        this.t_difs = t_difs;
        this.w = w;
        this.medium = medium;
        this.stNum = stNum;
        this.M = M;
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
            t_tot_all+= t_tot;            
        }
        System.out.println("Finished Staion "+stNum+" with total time of all packets :"+t_tot_all+" ticks.");
        
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