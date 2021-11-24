import java.util.ArrayList;

public class Ride {
    private int id;
    private Customer customer;
    String source;
    String destination;
    private ArrayList<Offer> offers;
    private RideState state;

    public Ride(Customer customer, String source, String destination) {
        this.customer = customer;
        this.source = source;
        this.destination = destination;
        offers = new ArrayList<>();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RideState getState() {
        return state;
    }

    public void setState(RideState state) {
        this.state = state;
    }



    public void addOffer(Offer offer) {
        ApplicationSystem.addOfferToRide(offer, this);
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    // Helper method for GUI
    public String getName() {
        return customer.username;
    }

}
