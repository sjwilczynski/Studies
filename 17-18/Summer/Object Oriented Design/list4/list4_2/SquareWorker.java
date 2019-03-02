package list4_2;

public class SquareWorker implements ShapeFactoryWorker {
    @Override
    public boolean acceptParameters(String shapeName, Object[] parameters) {
        return shapeName.equals("square") && parameters.length == 1 && parameters[0] instanceof Integer;
    }

    @Override
    public Shape createShape(String shapeName, Object[] parameters) {
        return new Square((Integer) parameters[0]);
    }
}

class RectangleWorker implements ShapeFactoryWorker {

    @Override
    public boolean acceptParameters(String shapeName, Object[] parameters) {
        return shapeName.equals("rectangle") && parameters.length == 2 && parameters[0] instanceof Integer && parameters[1] instanceof Integer;
    }

    @Override
    public Shape createShape(String shapeName, Object[] parameters) {
        return new Rectangle((Integer) parameters[0], (Integer) parameters[1]);
    }
}

class Square implements Shape {
    private Integer size;

    Square(int size) {
        this.size = size;
    }
}

class Rectangle implements Shape {
    private Integer width;
    private Integer height;

    Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

}
