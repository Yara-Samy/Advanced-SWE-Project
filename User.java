public abstract class User {
    protected String username;
    protected String password;
    protected String phoneNumber;
    protected String email;
    protected int isSuspended;

    public User(String username, String password, String phoneNumber, String email, int isSuspended) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isSuspended = isSuspended;
    }

    public User(String username, String password) {
        this(username, password, null, null, 0);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(int isSuspended) {
        this.isSuspended = isSuspended;
    }

    public abstract int login();

    public abstract int register();
}
