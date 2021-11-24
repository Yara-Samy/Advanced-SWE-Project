import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Customer extends User {

    public Customer(String username, String password, String phoneNumber, String email, int isSuspended) {
        super(username, password, phoneNumber, email, isSuspended);
    }

    public Customer(String username, String password, String phoneNumber, String email) {
        this(username, password, phoneNumber, email, 0);
    }

    public Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public int login() {
        User databaseCustomer;
        try {
            databaseCustomer = ApplicationSystem.getUsers().stream().filter(user -> user.getUsername().equalsIgnoreCase(this.getUsername()) && user.getPassword().equals(this.getPassword())).findFirst().get();
            if (!(databaseCustomer instanceof Customer))
                return 0;
            return databaseCustomer.getIsSuspended() == 0 ? 1 : -1;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    @Override
    public int register() {
        if (ApplicationSystem.getUsers().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(this.getUsername())))
            return 0;
        ApplicationSystem.addUser(this);
        return 1;
    }

    public void requestRide(String source, String destination) {
        ApplicationSystem.addRide(new Ride(this, source, destination));
    }

    public void acceptOffer(Offer offer) {
        ApplicationSystem.acceptOffer(offer);
        offer.getRide().setState(RideState.Done);
    }

    public ArrayList<Ride> getMyRideRequests() {
        return (ArrayList<Ride>) ApplicationSystem.getRides().stream().filter(ride -> ride.getCustomer().getUsername().equalsIgnoreCase(this.getUsername())).collect(Collectors.toList());
    }

    public void rate(Driver driver,int rate){
        ApplicationSystem.addRate(driver,this,rate);
    }
}
