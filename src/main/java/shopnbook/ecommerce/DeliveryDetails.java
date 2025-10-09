package shopnbook.ecommerce;

public class DeliveryDetails {
    private String street;
    private String village;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String phone;

    public DeliveryDetails(String street, String village, String city, String state, String pinCode, String country, String phone) {
        this.street = street;
        this.village = (village != null && !village.trim().isEmpty()) ? village.trim() : null;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
        this.country = country;
        this.phone = phone;
    }

    // Getters
    public String getStreet() { return street; }
    public String getVillage() { return village; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPinCode() { return pinCode; }
    public String getCountry() { return country; }
    public String getPhone() { return phone; }

    // Setters
    public void setStreet(String street) { this.street = street; }
    public void setVillage(String village) { this.village = (village != null && !village.trim().isEmpty()) ? village.trim() : null; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    public void setCountry(String country) { this.country = country; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(street);
        if (village != null && !village.isEmpty()) {
            sb.append(", ").append(village);
        }
        sb.append(", ").append(city)
          .append(", ").append(state)
          .append(" - ").append(pinCode)
          .append(", ").append(country)
          .append(" (Phone: ").append(phone).append(")");
        return sb.toString();
    }
}
