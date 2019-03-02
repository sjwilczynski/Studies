package list3_4;

public class Task4 {
    public static void main(String[] args) {
        Shape rect = new Rectangle(2, 3);
        Shape square = new Square(5);
        System.out.println("Rectangle area is " + rect.calculateArea());
        System.out.println("Square area is " + square.calculateArea());
    }
}

abstract class Shape {
    public abstract int calculateArea();
}

class Rectangle extends Shape {

    private int width;
    private int height;

    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int calculateArea() {
        return height * width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

class Square extends Shape {
    private int size;

    Square(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int calculateArea() {
        return size * size;
    }
}
