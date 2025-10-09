package shopnbook.ecommerce;

public class DeliveryDetails {
    private String name;
    private String street;
    private String city;
    private String postalCode;
    private String country;
    private String contactNumber;

    public DeliveryDetails(String name, String street, String city, String postalCode, String country, String contactNumber) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public String getContactNumber() { return contactNumber; }

    // Setters (if needed for updates)
    public void setName(String name) { this.name = name; }
    public void setStreet(String street) { this.street = street; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setCountry(String country) { this.country = country; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(", ");
        sb.append(street).append(", ");
        sb.append(city).append(" ").append(postalCode).append(", ");
        sb.append(country);
        if (contactNumber != null && !contactNumber.isEmpty()) {
            sb.append(" (Contact: ").append(contactNumber).append(")");
        }
        return sb.toString();
    }
}
