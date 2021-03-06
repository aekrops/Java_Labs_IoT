package ua.lviv.iot.lawFirm.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.lviv.iot.lawFirm.spring.manager.LawFirmManager;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Lawyer extends AbstractServices {

    private static AtomicInteger numbersOfLawyers = new AtomicInteger(0);
    //many to one
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firm_id")
    @JsonIgnoreProperties("lawyers")
    private LawyerFirm lawyerFirm;

    @JsonIgnoreProperties("lawyers")
    @ManyToMany(mappedBy = "lawyers")
    private Set<Customer> customers;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private double pricePerHourInUAH;

    @Transient
    protected List<Services> services = new LinkedList<>();

    private int age;
    private int numberOfServices = 3;
    private int lengthOfName;
    @Transient
    LawFirmManager manager = new LawFirmManager();

    public Lawyer(String name, double pricePerHourInUAH, int age, boolean isCollectingEvidence, boolean isAdvice) {

        this.name = name;
        this.lengthOfName = name.length();
        this.pricePerHourInUAH = pricePerHourInUAH;
        this.age = age;

        if (isCollectingEvidence) {
            services.add(Services.COLLECTINGEVIDENCE);
            super.setCollectingEvidence(true);
        } else {
            super.setCollectingEvidence(false);
        }
        if (isAdvice) {
            services.add(Services.ADVICE);
            super.setAdvice(true);
        } else {
            super.setAdvice(false);
        }

        if (isAdvice) {
            numberOfServices++;
        }
        if (isCollectingEvidence) {
            numberOfServices++;
        }

        numbersOfLawyers.incrementAndGet();
        id = numbersOfLawyers.get();

        manager.addLawyers(this);
    }

    public LawyerFirm getLawyerFirm() {
        return lawyerFirm;
    }

    public void setLawyerFirm(LawyerFirm lawyerFirm) {
        this.lawyerFirm = lawyerFirm;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public void setNumberOfServices(int numberOfServices) {
        this.numberOfServices = numberOfServices;
    }

    public int getLengthOfName() {
        return lengthOfName;
    }

    public void setLengthOfName(int lengthOfName) {
        this.lengthOfName = lengthOfName;
    }

    public Lawyer(String name, double pricePerHourInUAH) {
        this(name, pricePerHourInUAH, 0, false, false);
    }

    public Lawyer(String name) {
        this(name, 0.0, 0, false, false);
    }

    public Lawyer() {
//        numbersOfLawyers.incrementAndGet();
//        id = numbersOfLawyers.get();
    }


    public String getHeaders() {
        return "name" + "," + "price per hour in hryvna" + "," + "age" + "," + super.getHeaders();
    }

    public String toCSV() {
        return getName() + "," + getPricePerHourInUAH() + "," + getAge() + "," + super.toCSV();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerHourInUAH() {
        return pricePerHourInUAH;
    }

    public void setPricePerHourInUAH(double pricePerHourInUAH) {
        this.pricePerHourInUAH = pricePerHourInUAH;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfServices() {
        return numberOfServices;
    }
}
