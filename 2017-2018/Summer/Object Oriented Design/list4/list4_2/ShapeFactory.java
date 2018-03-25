package list4_2;

import java.util.ArrayList;

public class ShapeFactory {

    private ArrayList<ShapeFactoryWorker> workers = new ArrayList();

    ShapeFactory() {
        workers.add(new SquareWorker());
    }

    public void registerWorker(ShapeFactoryWorker worker) {
        workers.add(worker);
    }

    public Shape createShape(String shapeName, Object[] parameters) {
        for (ShapeFactoryWorker worker : workers) {
            if (worker.acceptParameters(shapeName, parameters)) {
                return worker.createShape(shapeName, parameters);
            }
        }
        return null;
    }
}
