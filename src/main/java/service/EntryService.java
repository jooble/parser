package service;

import IO.HandlerFiles;
import dao.EntryDao;
import model.Entry;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class EntryService extends TimerTask {

    private EntryValidator validator = new EntryValidator();
    private ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue();
    private EntryDao entryDao;
    private EntryParser parser = new EntryParser();
    private ExecutorService executorService;
    private File validationXsd;
    private HandlerFiles handlerFiles;
    private File dirMonitoring;
    private File treatedFiles;
    private File errorFiles;
    private String[] format;

    public EntryService(Setting setting) {
        this.entryDao = setting.getEntryDao();
        this.executorService = setting.getExecutorService();
        this.handlerFiles = setting.getHandlerFiles();
        this.dirMonitoring = setting.getDirMonitoring();
        this.treatedFiles = setting.getTreatedFiles();
        this.errorFiles = setting.getErrorFiles();
        this.format = setting.getFormats();
        this.validationXsd = setting.getValidationXsd();
    }


    private void handle() {
        int size = queue.size();

        for (int i = 0; i < size; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    File file = queue.poll();
                    try {
                        entryDao.begin();

                        validator.validate(validationXsd, file);

                        Entry entry = parser.parse(file);

                        handlerFiles.moveFile(file, treatedFiles);

                        entryDao.insert(entry);

                        entryDao.commit();
                    } catch (Exception e) {
                        entryDao.rollback();

                        handlerFiles.moveFile(file, errorFiles);
                        System.out.println("Обработка не удалась - " + e);
                    }
                }
            });
        }
    }


    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public void run() {
        queue.addAll(handlerFiles.checkDirectory(dirMonitoring, format));
        handle();
    }
}
