package list8.list8_1;


public class FileManager {

    private FileManager(){}
    private static ThreadLocal<FileManager> instance;

    public static synchronized FileManager getInstance() {
        if (instance == null) {
            instance = ThreadLocal.withInitial(FileManager::new);
        }
        return instance.get();
    }

    void downloadFileFTP(String fileAddress){
        System.out.println("Download file by FTP from " + fileAddress);
    }

    void downloadFileHTTP(String fileAddress) {
        System.out.println("Download file by HTTP from " + fileAddress);
    }

    void createRandomFile(String filename) {
        System.out.println("Creating file " + filename);
    }

    void moveFile(String oldFilename, String newFilename) {
        System.out.println("Moving file " + oldFilename + " to " + newFilename);
    }
}
