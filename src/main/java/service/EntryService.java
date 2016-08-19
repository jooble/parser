package service;

import IO.HandlerFiles;
import dao.EntryDao;
import model.Entry;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

public class EntryService extends TimerTask {

    private EntryDao entryDao;
    private EntryParser parser = new EntryParser();
    private ExecutorService executorService;
    private ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue();
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

                        Entry entry = parser.parse(file);

                        handlerFiles.moveFile(file, treatedFiles);

                        entryDao.insert(entry);

                        entryDao.commit();
                    } catch (Exception e) {
                        entryDao.rollback();

                        handlerFiles.moveFile(file, errorFiles);
                        System.out.println("Обработка не удалась");
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
