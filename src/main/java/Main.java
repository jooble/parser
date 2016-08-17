import IO.HandlerFiles;
import IO.HandlerFilesImpl;
import dao.EntryDao;
import dao.EntryDaoImpl;
import service.EntryService;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {

        EntryDao dao = new EntryDaoImpl();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        HandlerFiles handlerFiles = new HandlerFilesImpl();

        File dirMonitoring = new File("C:\\Users\\Дмитрий\\Desktop\\parsing\\файлы для парсинга");
        File treatedFiles = new File("C:\\Users\\Дмитрий\\Desktop\\parsing\\обработанные файлы");
        File errorFiles = new File("C:\\Users\\Дмитрий\\Desktop\\parsing\\файлы обработка которых не удалась");
        String format = "XML";


        EntryService service = new EntryService(dao, executorService, handlerFiles, dirMonitoring, treatedFiles, errorFiles, format);

        service.run();
        service.shutdown();
    }
}
