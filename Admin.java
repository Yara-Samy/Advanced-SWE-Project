import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Admin {
    private String userName;
    private String password;
    public Admin(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public boolean login() {
        return ApplicationSystem.getAdmins().stream().anyMatch(admin -> admin.getUserName().equalsIgnoreCase(this.getUserName()) && admin.getPassword().equals(this.getPassword()));
    }
    public ArrayList<Driver> listAllPendingDrivers(){
        ArrayList<Driver> pendingDrivers = new ArrayList<>();
        for (User user : ApplicationSystem.getUsers()) {
            if (user instanceof Driver) {
                Driver driver = (Driver) user;
                if (driver.getIsVerified() == 0)
                    pendingDrivers.add(driver);
            }
        }
        return pendingDrivers;
    }
    public void verify(String userName){
        ApplicationSystem.verifyDriver(userName);
    }
    public void suspend(User user) {
        ApplicationSystem.suspendUser(user);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
