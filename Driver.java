import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Driver extends User {

    private String drivingLicence;
    private String nationalID;
    private ArrayList<String> favoriteAreas;
    private double avgRate;
    private int a;
    private int totalRates,rateCounts;
    private int isVerified;
    


    public Driver(String username, String password, String phoneNumber, String email, String drivingLicence, String nationalID, int isSuspended, int isVerified, int totalRates, int rateCounts, double avgRate) {
        super(username, password, phoneNumber, email, isSuspended);
        this.drivingLicence = drivingLicence;
        this.nationalID = nationalID;
        this.isVerified = isVerified;
        this.totalRates = totalRates;
        this.rateCounts = rateCounts;
        this.avgRate = avgRate;
        favoriteAreas = new ArrayList<>();
    }

    public Driver(String username, String password, String phoneNumber, String email, String drivingLicence, String nationalID) {
        this(username, password, phoneNumber, email, drivingLicence, nationalID, 0, 0, 0, 0, 0);
    }

    public Driver(String username, String password) {
        this(username, password, null, null, null, null);
    }

    @Override
    public int login() {
        User user;
        Driver databaseDriver;
        try {
            user = ApplicationSystem.getUsers().stream().filter(user1 -> user1.getUsername().equalsIgnoreCase(this.getUsername()) && user1.getPassword().equals(this.getPassword())).findFirst().get();
            if (user instanceof Driver) {
                databaseDriver = (Driver) user;
            } else
                return 0;
            if (databaseDriver.getIsVerified() == 0)
                return -2;
            if (databaseDriver.getIsSuspended() == 1)
                return -1;
            return 1;
        } catch (NoSuchElementException e) {
            return 0;
        }
    }

    @Override
    public int register() {
        if (ApplicationSystem.getUsers().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(this.getUsername())))
            return 0;
        for (User user : ApplicationSystem.getUsers()) {
            if (user instanceof Driver) {
                Driver driver = (Driver) user;
                if (driver.getNationalID().equals(this.getNationalID()))
                    return -1;
            }
        }
        ApplicationSystem.addUser(this);
        return 1;
    }

    public void offer(Ride ride, double price) {
        Offer offer = new Offer(this, price, ride, OfferState.Pending);
        ride.addOffer(offer);
    }

    public void addFavoriteArea(String area) {
        ApplicationSystem.addDriverFavorite(this, area);
    }

    public ArrayList<Ride> getPossibleRides() {
        return (ArrayList<Ride>) ApplicationSystem.getRides().stream().filter(ride -> favoriteAreas.contains(ride.getSource()) && ride.getState() == RideState.Ongoing).collect(Collectors.toList());
    }

    public ArrayList<Rating> listAllRatings(){
        return (ArrayList<Rating>) ApplicationSystem.getRatings().stream().filter(rate -> rate.getDriver().getUsername().equalsIgnoreCase(this.getUsername())).collect(Collectors.toList());
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public ArrayList<String> getFavoriteAreas() {
        return favoriteAreas;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public int getTotalRates() {
        return totalRates;
    }

    public void setTotalRates(int totalRates) {
        this.totalRates = totalRates;
    }

    public int getRateCounts() {
        return rateCounts;
    }

    public void setRateCounts(int rateCounts) {
        this.rateCounts = rateCounts;
    }



    @Override
    public String toString() {
        return this.getUsername();
    }
}
