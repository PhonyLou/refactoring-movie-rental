package cn.xpbootcamp.refactor;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {

    private String name;
    private Vector<Rental> rentals = new Vector<>();

    Customer(String name) {
        this.name = name;
    }

    void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public String getName() {
        return name;
    }

    String statement() {
        double totalAmount = 0d;
        int frequentRenterPoints = 0;
        Enumeration<Rental> rentals = this.rentals.elements();
        StringBuilder result = new StringBuilder("Rental Record for " + getName() + "：\n");
        while (rentals.hasMoreElements()) {
            Rental each = rentals.nextElement();
            frequentRenterPoints = increaseFrequentRenterPoints(frequentRenterPoints, each);
            totalAmount += determineAmount(each);

            result.append(figures(each));
        }
        //add footer lines
        result.append("Amount owed is ").append(totalAmount).append("\n");
        result.append("You earned ").append(frequentRenterPoints).append(" frequent renter points");
        return result.toString();
    }

    private StringBuilder figures(Rental each) {
        StringBuilder result = new StringBuilder();
        result.append("\t")
              .append(each.getMovie().getTitle())
              .append("\t")
              .append(determineAmount(each)).append("\n");
        return result;
    }

    private int increaseFrequentRenterPoints(final int frequentRenterPoints, final Rental rental) {
        int result = frequentRenterPoints + 1;
        if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE) && rental.getDaysRented() > 1)
            result++;
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
