import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationSystem {

    private static ArrayList<Admin> admins;
    private static Database database;
    private static ArrayList<User> users;
    private static ArrayList<Ride> rides;
    private static ArrayList<Offer> offers;
    private static ArrayList<Rating> ratings;
    private static boolean isInitialized;


    public static void init() {
        if (isInitialized)
            return;
        admins = new ArrayList<>();
        database = new Database("database.db");
        users = new ArrayList<>();
        rides = new ArrayList<>();
        offers = new ArrayList<>();
        ratings = new ArrayList<>();
        getData();
        isInitialized = true;
    }

    private static void getData() {
        ResultSet allCustomers = database.getAllTable("Customer");
        try {
            while (allCustomers != null && allCustomers.next())
                users.add(new Customer(allCustomers.getString("username"), allCustomers.getString("password"), allCustomers.getString("phoneNumber"), allCustomers.getString("email"), allCustomers.getInt("isSuspended")));

            ResultSet allDrivers = database.getAllTable("Driver");
            while (allDrivers != null && allDrivers.next())
                users.add(new Driver(allDrivers.getString("username"), allDrivers.getString("password"), allDrivers.getString("phoneNumber"), allDrivers.getString("email"), allDrivers.getString("licenceNumber"), allDrivers.getString("nationalID"), allDrivers.getInt("isSuspended"), allDrivers.getInt("isVerified"), allDrivers.getInt("totalRates"), allDrivers.getInt("ratesCounts"), allDrivers.getDouble("avgRate")));

            ResultSet allRides = database.getAllTable("Ride");
            while (allRides != null && allRides.next()) {
                Ride ride = new Ride((Customer) users.stream().filter(user -> {
                    try {
                        return user.getUsername().equalsIgnoreCase(allRides.getString("customer"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst().get(), allRides.getString("source"), allRides.getString("destination"));
                ride.setId(allRides.getInt("id"));
                ride.setState(RideState.valueOf(allRides.getString("state")));

                rides.add(ride);
            }
            ResultSet allDriversFavorites = database.getAllTable("Favorite");
            while (allDriversFavorites != null && allDriversFavorites.next()) {
                Driver driver = (Driver) users.stream().filter(user -> {
                    try {
                        return user.getUsername().equalsIgnoreCase(allDriversFavorites.getString("driver"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst().get();
                String source = allDriversFavorites.getString("area");
                driver.getFavoriteAreas().add(source);
                //driversFavorites.add(new Pair<>(driver, source));
            }



            ResultSet allRidesOffers = database.getAllTable("Offer");
            while (allRidesOffers != null && allRidesOffers.next()) {
                Offer newOffer = new Offer((Driver) users.stream().filter(user -> {
                    try {
                        return user.getUsername().equalsIgnoreCase(allRidesOffers.getString("driver"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst().get(), allRidesOffers.getDouble("price"));

                Ride newRide = rides.stream().filter(ride -> {
                    try {
                        return ride.getId() == allRidesOffers.getInt("ride");
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst().get();
                newRide.getOffers().add(newOffer);
                newOffer.setRide(newRide);
                newOffer.setState(OfferState.valueOf(allRidesOffers.getString("state")));
                offers.add(newOffer);
                //rideOffers.add(new Pair<>(newOffer, newRide));
            }

            ResultSet allRatings = database.getAllTable("Rating");
            while (allRatings!=null && allRatings.next()) {
                Rating rating = new Rating((Driver) users.stream().filter(user -> {
                    try {
                        return user.getUsername().equalsIgnoreCase(allRatings.getString("driver"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst().get(), (Customer) users.stream().filter(user -> {
                    try {
                        return user.getUsername().equalsIgnoreCase(allRatings.getString("customer"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }).findFirst().get(), allRatings.getInt("rate"));
                ratings.add(rating);
            }


            ResultSet allAdmins = database.getAllTable("Admin");
            while (allAdmins != null && allAdmins.next()) {
                admins.add(new Admin(allAdmins.getString("username"), allAdmins.getString("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            database.executeUpdate(String.format("INSERT INTO Customer VALUES('%s', '%s', '%s', %s, %d)", customer.getUsername(), customer.getPassword(), customer.getPhoneNumber(), customer.getEmail() != null ? "'" + customer.getEmail() + "'" : null, 0));
        } else if (user instanceof Driver) {
            Driver driver = (Driver) user;
            database.executeUpdate(String.format("INSERT INTO Driver VALUES('%s', '%s', '%s', %s, '%s', '%s', %d, %d, %d, %d, %d)", driver.getUsername(), driver.getPassword(), driver.getPhoneNumber(), driver.getEmail() != null ? "'" + driver.getEmail() + "'" : null, driver.getDrivingLicence(), driver.getNationalID(), 0, 0, 0, 0, 0));
        }
        users.add(user);
    }

    public static void addRide(Ride ride) {
        database.executeUpdate(String.format("INSERT INTO Ride (customer, source, destination, state) VALUES('%s', '%s', '%s', '%s')", ride.getCustomer().getUsername(), ride.getSource(), ride.getDestination(), RideState.Ongoing));
        try {
            ride.setId(database.executeQuery(String.format("SELECT id FROM Ride WHERE customer = '%s' AND source = '%s' AND destination = '%s'", ride.getCustomer().getUsername(), ride.getSource(), ride.getDestination())).getInt("id"));
            ride.setState(RideState.Ongoing);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rides.add(ride);
    }

    public static void addDriverFavorite(Driver driver, String source) {
        database.executeUpdate(String.format("INSERT INTO favorite VALUES('%s', '%s')", driver.getUsername(), source));
        Driver databaseDriver = (Driver) users.stream().filter(user -> user.getUsername().equalsIgnoreCase(driver.getUsername())).findFirst().get(); // to test
        databaseDriver.getFavoriteAreas().add(source);
        //driversFavorites.add(pair);
    }

    public static void addOfferToRide(Offer offer, Ride ride) {
        database.executeUpdate(String.format("INSERT INTO Offer VALUES('%s', %d, %g, '%s')", offer.getDriver().getUsername(), ride.getId(), offer.getPrice(), offer.getState()));
        Ride databaseRide = rides.stream().filter(ride1 -> ride1.getId() == ride.getId()).findFirst().get();
        databaseRide.getOffers().add(offer);
        //Offer databaseOffer = offers.stream().filter(offer1 -> offer1.getRide().getId() == databaseRide.getId()).findFirst().get();
        offer.setRide(databaseRide);
        offers.add(offer);

        //rideOffers.add(pair);
    }

    public static void acceptOffer(Offer offer) {
        database.executeUpdate(String.format("UPDATE Ride SET state = '%s' WHERE id = %d", RideState.Done, offer.getRide().getId()));
        database.executeUpdate(String.format("UPDATE Offer SET state = '%s' WHERE ride = %d AND driver = '%s'", OfferState.Accepted, offer.getRide().getId(), offer.getDriver().getUsername()));
        Ride ride = rides.stream().filter(ride1 -> ride1.getId() == offer.getRide().getId()).findFirst().get();
        Offer databaseOffer = offers.stream().filter(offer1 -> offer1.getRide().getId() == offer.getRide().getId() && offer1.getDriver().getUsername().equalsIgnoreCase(offer.getDriver().getUsername())).findFirst().get();
        databaseOffer.setState(OfferState.Accepted);
        ride.setState(RideState.Done);
    }

    public static void addRate(Driver driver,Customer customer,int rate){
        Driver databaseDriver = (Driver) users.stream().filter(user -> user.getUsername().equalsIgnoreCase(driver.getUsername())).findFirst().get();
        Customer databaseCustomer = (Customer) users.stream().filter(user -> user.getUsername().equalsIgnoreCase(customer.getUsername())).findFirst().get();
        database.executeUpdate(String.format("Insert into Rating(driver, customer, rate) Values ('%s','%s',%d)",databaseDriver.getUsername(),databaseCustomer.getUsername(),rate));
        ratings.add(new Rating(databaseDriver, databaseCustomer, rate));
        database.executeUpdate(String.format("UPDATE Driver SET totalRates = totalRates+%d , ratesCounts = ratesCounts+1 Where username ='%s'",rate,databaseDriver.getUsername()));
        database.executeUpdate(String.format("UPDATE Driver SET avgRate = 1.0*totalRates/ratesCounts Where username ='%s'",databaseDriver.getUsername()));
        databaseDriver.setTotalRates(databaseDriver.getTotalRates()+rate);
        databaseDriver.setRateCounts(databaseDriver.getRateCounts()+1);
        databaseDriver.setAvgRate(1.0*databaseDriver.getTotalRates()/ databaseDriver.getRateCounts());
    }

    public static void verifyDriver(String username) {
        database.executeUpdate(String.format("UPDATE Driver SET isVerified = 1 WHERE username = '%s'", username));
        Driver driver = (Driver) getUsers().stream().filter(user -> user.getUsername().equalsIgnoreCase(username)).findFirst().get();
        driver.setIsVerified(1);
    }

    public static void suspendUser(User user) {
        if (user instanceof Customer)
            database.executeUpdate(String.format("UPDATE Customer SET isSuspended = 1 WHERE username = '%s'", user.getUsername()));
        else if (user  instanceof Driver)
            database.executeUpdate(String.format("UPDATE Driver SET isSuspended = 1 WHERE username = '%s'", user.getUsername()));
        User databaseUser = getUsers().stream().filter(user1 -> user1.getUsername().equalsIgnoreCase(user.getUsername())).findFirst().get();
        databaseUser.setIsSuspended(1);
    }

    public static void terminate() {
        database.disconnect();
        isInitialized = false;
    }

    public static ArrayList<Admin> getAdmins() {
        return admins;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<Ride> getRides() {
        return rides;
    }

    public static ArrayList<Offer> getOffers() {
        return offers;
    }

    public static ArrayList<Rating> getRatings() {
        return ratings;
    }

//    public static ArrayList<Pair<Driver, String>> getDriversFavorites() {
//        return driversFavorites;
//    }
//
//    public static ArrayList<Pair<Offer, Ride>> getRideOffers() {
//        return rideOffers;
//    }
}
