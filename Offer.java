public class Offer {
    private Driver driver;
    private double price;
    private Ride ride;
    private OfferState state;

    public Offer(Driver driver, double price, Ride ride, OfferState state) {
        this.driver = driver;
        this.price = price;
        this.ride = ride;
        this.state = state;
    }

    public Offer(Driver driver, double price) {
        this(driver, price, null, null);
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public OfferState getState() {
        return state;
    }

    public void setState(OfferState state) {
        this.state = state;
    }

    // Helper method for GUI
    public double getAvgRate() {
        return driver.getAvgRate();
    }
}
