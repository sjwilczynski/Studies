package list8.list8_1;

public interface Command {
    void execute();
}

class DownloadFileFTP implements Command {
    private String fileAddress;

    DownloadFileFTP(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    @Override
    public void execute() {
        FileManager.getInstance().downloadFileFTP(fileAddress);
    }
}

class DownloadFileHTTP implements Command {
    private String fileAddress;

    DownloadFileHTTP(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    @Override
    public void execute() {
        FileManager.getInstance().downloadFileHTTP(fileAddress);
    }
}

class RandomFileCreation implements Command {
    private String filename;

    RandomFileCreation(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() {
        FileManager.getInstance().createRandomFile(filename);
    }
}

class MovingFile implements Command {
    private String oldFilename;
    private String newFilename;

    MovingFile(String oldFilename, String newFilename) {
        this.oldFilename = oldFilename;
        this.newFilename = newFilename;
    }

    @Override
    public void execute() {
        FileManager.getInstance().moveFile(oldFilename, newFilename);
    }
}
