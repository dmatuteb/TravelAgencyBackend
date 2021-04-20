package io.github.dmatuteb.TravelAgency.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_sequence", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "employee_name", length = 20, nullable = false)
    @Size(min = 1, max = 20)
    @NotBlank(message = "name is mandatory")
    private String name;

    @Column(name = "employee_lastname", length = 20, nullable = false)
    @Size(min = 1, max = 20)
    @NotBlank(message = "lastname is mandatory")
    private String lastname;

    @Column(name = "employee_address", length = 1024, nullable = false)
    @Size(min = 1, max = 1024)
    @NotBlank(message = "address is mandatory")
    private String address;

    @Column(name = "employee_phone_number", length = 20, nullable = false)
    @Size(min = 1, max = 20)
    @NotBlank(message = "phone number is mandatory")
    @Pattern(regexp = "[0-9]{4}-[0-9]{4}", message = "please match the requested phone number format")
    private String phoneNumber;

    @Column(name = "employee_email", length = 100, nullable = false)
    @Size(min = 1, max = 100)
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;

    @ManyToOne
    @JoinColumn(name = "employee_amusement_park_id", referencedColumnName = "amusement_park_id")
    private AmusementPark employeeAmusementPark;

    public Employee() {
    }

    public Employee(Long id, String name, String lastname, String address, String phoneNumber, String email, AmusementPark amusementPark) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.employeeAmusementPark = amusementPark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public AmusementPark getAmusementPark() {
        return employeeAmusementPark;
    }

    public void setAmusementPark(AmusementPark amusementPark) {
        this.employeeAmusementPark = amusementPark;
    }

}
