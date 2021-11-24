public class Rating {
    private Driver driver;
    private Customer customer;
    private int rate;
    // private int id;

    public Rating(Driver driver,Customer customer,int rate){
        this.customer = customer;
        this.driver = driver;
        this.rate = rate;
        //this.id = id;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return String.format("From %s value %d", customer.getUsername(), rate);
    }

    // Helper method for GUI
    public String getName() {
        return customer.username;
    }
}
