package list3_2;

public class Task2 {

    public static void main(String[] args) {
        String data = "Document doc doc";
        ReportPrinterOld rpo = new ReportPrinterOld(data);
        DocData doc = new DocData(data);
        DocFormatter df = new DocFormatter();
        DocPrinter dp = new DocPrinter();

        rpo.formatDocument();
        rpo.printReport();
        df.formatDocument(doc);
        dp.printReport(doc);

    }
}

class ReportPrinterOld {
    private String data;
    ReportPrinterOld(String data){
        this.data = data;
    }
    public String getData(){
        return data;
    }

    public void formatDocument(){
        data += "\nFormatted\n";
    }
    public void printReport(){
        System.out.println("Printed data: " + data );
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

class DocFormatter {
    public void formatDocument(DocData doc){
        doc.setData(doc.getData() + "\nFormatted (new way)\n");
    }
}

class DocPrinter {
    public void printReport(DocData doc){
        System.out.println("Printed data: " + doc.getData());
    }
}
