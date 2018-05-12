package list8.list8_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataAccessHandlerTest {

    @Test
    void testDatabaseHandler() {
        //put here correct data
        String database = "jdbc:postgresql://<host>:<port>/<dbname>?sslmode=require";
        String user = "<username>";
        String password = "<password>";
        String query = "select * from history";
        DatabaseAccessHandler dbah = new DatabaseAccessHandler(database, user, password, query, "winner", "stachu");
        dbah.execute();
        assertTrue(dbah.getResult() > 40);
    }

    @Test
    void testXMLAccessHandler() {
        XMLAccessHandler xmlAccessHandler = new XMLAccessHandler("src/list8/list8_2/note.xml");
        xmlAccessHandler.execute();
        assertEquals(7, xmlAccessHandler.getMaxLength());
    }
}