class Medium extends Thread {
    private boolean busy = false;

    public synchronized boolean isBusy() {
        return busy;
    }

    public synchronized void setBusy(boolean val) {
        this.busy = val;
    }
}