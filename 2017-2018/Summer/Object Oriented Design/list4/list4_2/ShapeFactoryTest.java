package list4_2;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShapeFactoryTest {

    @Test
    void testDefaultWorker() {
        ShapeFactory factory = new ShapeFactory();
        Shape square = factory.createShape("square", new Integer[]{4});
        assertNotNull(square);
        assertTrue(square instanceof Square);
    }

    @Test
    void testNotPresentWorker() {
        ShapeFactory factory = new ShapeFactory();
        Shape shape = factory.createShape("circle", new Integer[]{5, 6, 7});
        assertNull(shape);
    }

    @Test
    void testAddingWorker() {
        ShapeFactory factory = new ShapeFactory();
        factory.registerWorker(new RectangleWorker());
        Shape rectangle = factory.createShape("rectangle", new Integer[]{5, 6});
        assertNotNull(rectangle);
        assertTrue(rectangle instanceof Rectangle);
    }
}