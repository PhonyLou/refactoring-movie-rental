package cn.xpbootcamp.refactor;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {

    private String name;
    private Vector<Rental> rentals = new Vector<>();


    Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Vector<Rental> getRentals() {
        return rentals;
    }

    void addRental(Rental rental) {
        getRentals().addElement(rental);
    }


    String statement() {
        double totalAmount = totalAmount(getRentals());
        int frequentRenterPoints = frequentRenterPoints(getRentals());
        StringBuilder result = renderReceipt(totalAmount, frequentRenterPoints);

        return result.toString();
    }

    private double totalAmount(final Vector<Rental> rentals) {
        double result = 0d;
        Enumeration<Rental> r = rentals.elements();
        while (r.hasMoreElements()) {
            Rental each = r.nextElement();
            result += determineAmount(each);
        }
        return result;
    }

    private StringBuilder renderReceipt(double totalAmount, int frequentRenterPoints) {
        StringBuilder result = title(getName());
        getRentals().forEach(r -> result.append(details(r.getMovie().getTitle(), determineAmount(r))));
        result.append(footerLines(totalAmount, frequentRenterPoints));
        return result;
    }

    private StringBuilder title(final String name) {
        return new StringBuilder("Rental Record for " + name + "：\n");
    }

    private StringBuilder footerLines(double totalAmount, int frequentRenterPoints) {
        return new StringBuilder()
                .append("Amount owed is ").append(totalAmount).append("\n")
                .append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
    }

    private StringBuilder details(final String movieTitle, final double amount) {
        return new StringBuilder()
                .append("\t")
                .append(movieTitle)
                .append("\t")
                .append(amount).append("\n");
    }

    private int frequentRenterPoints(final Vector<Rental> rentals) {
        int result = 0;
        for (Rental rental : rentals) {
            result++;
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1)
                result++;
        }

        return result;
    }

    private double determineAmount(final Rental rental) {
        double result = 0d;
        switch (rental.getMovie().getPriceCode()) {
            case Movie.HISTORY:
                result += 2;
                if (rental.getDaysRented() > 2)
                    result += (rental.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                result += rental.getDaysRented() * 3;
                break;
            case Movie.CAMPUS:
                result += 1.5;
                if (rental.getDaysRented() > 3)
                    result += (rental.getDaysRented() - 3) * 1.5;
                break;
        }
        return result;
    }

}
