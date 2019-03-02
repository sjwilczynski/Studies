package list3_7;

//task6 - the idea is the same. The difference is in the point of view
// SRP is from a designer point of view, ISP is from a client point of view

//Doesn't it violate SRP
public class Task7 {

    public static void main(String[] args) {
        String data = "Document doc doc";
        DocData doc = new DocData(data);
        ReportComposer rc = new ReportComposer(doc, new DocFormatter(), new DocPrinter());
        rc.composeReport();
    }
}

class ReportComposer {
    private DocData data;
    private Formatter formatter;
    private Printer printer;

    ReportComposer(DocData data, Formatter formatter, Printer printer) {
        this.data = data;
        this.formatter = formatter;
        this.printer = printer;
    }

    public void composeReport() {
        formatter.formatDocument(data);
        printer.printReport(data);
    }
}

class DocData {

    private String data;

    DocData(String data) {
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

class DocFormatter implements Formatter {
    @Override
    public void formatDocument(DocData doc) {
        doc.setData(doc.getData() + "\nFormatted (new way)\n");
    }
}

class DocPrinter implements Printer {
    @Override
    public void printReport(DocData doc) {
        System.out.println("Printed data: " + doc.getData());
    }
}
