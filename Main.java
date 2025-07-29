import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carNo;
    private String brand;
    private String model;
    private double farePerDay;
    private boolean isAvailable;

    public Car(String carNo, String brand, String model, double farePerDay) {
        this.carNo = carNo;
        this.brand = brand;
        this.model = model;
        this.farePerDay = farePerDay;
        this.isAvailable = true;
    }
    public String getCarNo() {
        return carNo;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return farePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }
//Methods to Acess customer ID and Name
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) //car type and customer type 
    {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
//Methods to acess car,customer and days 
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}
//Make an array list to store the data 
class CarRentalSystem {

    //Array list creation without memory allocation

    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    //Now memory alloction for array list through "new" keyword amd create blank array list 

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

//Check the availabilty of the car and calculate fare according to number of days

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));

        } else {
            System.out.println("Car is not available for rent.");
        }
    }
//Returning of the Car
    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null; //Null for checking
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
    

//Basic Structure to Make a system in a 3 conditons 
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarNo() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car No: you want to rent: ");
                String carNo= scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline
//Assign the Cutomer ID to the cutomer(Cus1,Cus2,Cus3 .....)
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarNo().equals(carNo) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();
                       //ignore case is a function
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car Number you want to return: ");
                String CarNo = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarNo().equals(CarNo) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car No or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

}
public class Main{
    public static void main(String[] args) 
    {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Land Cruiser",100.0); // Different base price per day for each car
        Car car2 = new Car("C002", "Toyota", "Corolla",75.0); 
        Car car3 = new Car("C003", "Honda", "Civic", 82.0);
        Car car4 = new Car("C004", "Honda", "BR-V(Suv)", 90.0);
        Car car5 = new Car("C005", "Suzuki", "Alto", 37.0);
        Car car6 = new Car("C006", "Suzuki", "Waganor", 45.0);
        Car car7 = new Car("C007", "Hyundai", "Sonata", 90.0);
        Car car8 = new Car("C008", "Hyundai", "Tucson", 80.5);
        Car car9 = new Car("C009", "Kia", "Shehzore(LOADER)",160.0);
        Car car10 = new Car("C0010", "Mazda", "Mazda Titan(Truck)", 220.0);
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);
        rentalSystem.addCar(car7);
        rentalSystem.addCar(car8);
        rentalSystem.addCar(car9);
        rentalSystem.addCar(car10);
    

        //callint the menu for struture
        rentalSystem.menu();
    }
}
