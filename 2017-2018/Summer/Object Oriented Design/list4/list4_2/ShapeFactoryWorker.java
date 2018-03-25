package list4_2;

public interface ShapeFactoryWorker {
    boolean acceptParameters(String shapeName, Object[] parameters);

    Shape createShape(String shapeName, Object[] parameters);
}

interface Shape {

}
