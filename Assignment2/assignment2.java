package Assignment2;

import java.util.Scanner;

abstract class Shape {
    abstract void calculate();
}

class Circle extends Shape {
    private double r;

    public Circle(double r) {
        this.r = r;
    }

    void calculate() {
        System.out.println("Area: " + (Math.PI * r * r));
        System.out.println("Circumference: " + (2 * Math.PI * r));
    }
}

class Rectangle extends Shape {
    private double l, w;

    public Rectangle(double l, double w) {
        this.l = l;
        this.w = w;
    }

    void calculate() {
        System.out.println("Area: " + (l * w));
        System.out.println("Perimeter: " + (2 * (l + w)));
    }
}

class Triangle extends Shape {
    private double b, h;

    public Triangle(double b, double h) {
        this.b = b;
        this.h = h;
    }

    void calculate() {
        System.out.println("Area: " + (0.5 * b * h));
    }
}

class Square extends Shape {
    private double s;

    public Square(double s) {
        this.s = s;
    }

    void calculate() {
        System.out.println("Area: " + (s * s));
        System.out.println("Perimeter: " + (4 * s));
    }
}

class Sphere extends Shape {
    private double r;

    public Sphere(double r) {
        this.r = r;
    }

    void calculate() {
        System.out.println("Surface Area: " + (4 * Math.PI * r * r));
        System.out.println("Volume: " + ((4.0 / 3.0) * Math.PI * r * r * r));
    }
}

class Cylinder extends Shape {
    private double r, h;

    public Cylinder(double r, double h) {
        this.r = r;
        this.h = h;
    }

    void calculate() {
        double base = Math.PI * r * r, lat = 2 * Math.PI * r * h;
        System.out.println("Surface Area: " + (2 * base + lat));
        System.out.println("Volume: " + (base * h));
    }
}

public class assignment2 {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1.Circle 2.Rectangle 3.Triangle 4.Square 5.Sphere 6.Cylinder 0.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            if (ch == 0)
                break;

            Shape shape = switch (ch) {
                case 1 -> new Circle(getInput("Enter radius: "));
                case 2 -> new Rectangle(getInput("Enter length: "), getInput("Enter width: "));
                case 3 -> new Triangle(getInput("Enter base: "), getInput("Enter height: "));
                case 4 -> new Square(getInput("Enter side: "));
                case 5 -> new Sphere(getInput("Enter radius: "));
                case 6 -> new Cylinder(getInput("Enter radius: "), getInput("Enter height: "));
                default -> null;
            };

            if (shape != null)
                shape.calculate();
            else
                System.out.println("Invalid choice!");
        }
    }

    private static double getInput(String prompt) {
        System.out.print(prompt);
        return sc.nextDouble();
    }
}
