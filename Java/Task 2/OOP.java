class Mammal {
    public void Fact() {
        System.out.println("Mammals generally don't lay eggs");
    }
}

// Inheritance 
class Platypus extends Mammal {
    @Override
    public void Fact() {
        System.out.println("Platypuses (Yes, the plural is in fact platypuses and not platypi though they can be used): They're the only mammals that lay eggs");
    }

    public void toTheTop() {
        super.Fact();
    }
}

class RandomMath {

    private int result;

    // Encapsulation
    // fetching the private variable result
    public int getResult() {
        return result;
    }

    // setting a new value to the private variable result
    public void setResult(int result) {
        this.result = result;
    }
    
    public int add(int num1, int num2) {
        this.setResult(num1 + num2);
        return this.getResult();
    }
    // Method Overloading
    public int add(int num1, int num2, int num3) {
        this.setResult(num1 + num2 + num3);
        return this.getResult();
    }
}

// Data Abstraction
interface HouseBlueprint {
    public void Rooms(int numberOfRooms);
}

interface Building {
    public void Stairs(int numberOfSteps);
}

// Multiple Inheritance
class newHouse implements HouseBlueprint, Building {

    private int rooms;
    private int stairs;
    // Constructor
    newHouse(int newRooms, int newStairs) {
        this.Rooms(newRooms);
        this.Stairs(newStairs);
    }

    newHouse() {
        this.Rooms(0);
        this.Stairs(0);
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getStairs() {
        return stairs;
    }

    public void setStairs(int stairs) {
        this.stairs = stairs;
    }

    @Override
    public void Rooms(int numberOfRooms) {
        setRooms(numberOfRooms);
    }

    @Override
    public void Stairs(int numberOfSteps) {
        setStairs(numberOfSteps);
    }

}


public class OOP {
    public static void main(String[] args) {
        
    }
}
