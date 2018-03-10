import java.util.UUID;

class Car {
    void run()
    {
        System.out.println("car is running");
    }
}
class Audi extends Car {
    void run()
    {
        System.out.println("Audi is running safely with 100km");
    }
}

class Base {
    private static void display() {
        System.out.println("Static or class method from Base");
    }
    public void print() {
        System.out.println("Non-static or instance method from Base");
    }
}

class Derived extends Base {
    private static void display() {
        System.out.println("Static or class method from Derived");
    }
    public void print() {
        System.out.println("Non-static or instance method from Derived");
    }
}

public class test {
    public static void main(String arg[]){
        String randomStr = UUID.randomUUID().toString();
        System.out.println(randomStr);

        String strA = "ABC";
        String strB = "ABC";

        System.out.println("strA == strB " + (strA == strB));
        System.out.println("strA.equals(strB)" + (strA.equals(strB)));

        String strC = new String("ABC");
        System.out.println("strA == strC " + (strA == strC));

        //Runtime polymorphism or Dynamic method dispatch
        Car b = new Audi();
        b.run();

        //Can't override private or static methods
        Base base = new Derived();
        // Uncomment the line below will throw an error.
        //base.display();
        base.print();
    }
}
