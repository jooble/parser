package service;

import IO.HandlerFiles;
import dao.EntryDao;
import model.Entry;

import java.io.File;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
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

    public EntryService(EntryDao entryDao,
                        ExecutorService executorService,
                        HandlerFiles handlerFiles,
                        File dirMonitoring,
                        File treatedFiles,
                        File errorFiles,
                        String... format) {
        this.entryDao = entryDao;
        this.executorService = executorService;
        this.handlerFiles = handlerFiles;
        this.dirMonitoring = dirMonitoring;
        this.treatedFiles = treatedFiles;
        this.errorFiles = errorFiles;
        this.format = format;
    }

    public void handle() throws ExecutionException, InterruptedException {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (!queue.isEmpty()) {
                    File file = queue.poll();
                    try {
                        Entry entry = parser.parse(file);
                        handlerFiles.moveFile(file, treatedFiles);
                        entryDao.insert(entry);
                    } catch (Exception e) {
                        //TODO
                        System.out.println("Обработка не удалась");
                        handlerFiles.moveFile(file, errorFiles);
                    }
                }
            }
        });
    }

    @Override
    public void run() {
        queue.addAll(handlerFiles.checkDirectory(dirMonitoring, format));
        try {
            handle();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
