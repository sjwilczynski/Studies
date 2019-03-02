package list8.list8_2;

import org.apache.commons.dbutils.DbUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatabaseAccessHandler extends DataAccessHandler {

    private Connection connection;
    private String database;
    private String user;
    private String password;
    private String query;
    private String column;
    private String name;
    private ResultSet rs;
    private Statement stmt;

    public int getResult() {
        return result;
    }

    private int result;
    private List<String> userNames;

    DatabaseAccessHandler(String database, String user, String password, String query, String column, String name) {
        this.database = database;
        this.user = user;
        this.password = password;
        this.query = query;
        this.column = column;
        this.name = name;
        userNames = new ArrayList<>();
    }

    @Override
    void openConnection() throws ConnectionException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(database, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    @Override
    void getData() throws ConnectionException {
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String winner = rs.getString(column);
                if (winner != null) {
                    userNames.add(winner);
                }
            }
        } catch (SQLException e) {
            throw new ConnectionException(e.getMessage());
        }

    }

    @Override
    void processData() {
        result = Collections.frequency(userNames, name);
    }

    @Override
    void closeConnection() {
        DbUtils.closeQuietly(rs);
        DbUtils.closeQuietly(stmt);
        DbUtils.closeQuietly(connection);
    }
}


class XMLAccessHandler extends DataAccessHandler {

    private String path;
    private Document document;

    private ArrayList<String> tags;

    public int getMaxLength() {
        return maxLength;
    }

    private int maxLength;

    XMLAccessHandler(String path) {
        this.path = path;
        tags = new ArrayList<>();
    }

    @Override
    void openConnection() throws ConnectionException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(path);
        } catch (Exception e) {
            throw new ConnectionException(e.getMessage());
        }
    }

    @Override
    void getData() {
        VisitNode(document.getDocumentElement());
    }

    @Override
    void processData() {
        maxLength = Collections.max(tags, Comparator.comparing(String::length)).length();
    }

    @Override
    void closeConnection() {
        //nothing to do here
    }

    private void VisitNode(Node node) {
        tags.add(node.getNodeName());

        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            VisitNode(child);
        }
    }
}
