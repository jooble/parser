package service;

import IO.HandlerFiles;
import IO.HandlerFilesImpl;
import dao.EntryDao;
import dao.EntryDaoImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Setting {
    private File dirMonitoring = new File("D:\\parsing\\parsing");
    private File treatedFiles = new File("D:\\parsing\\treated");
    private File errorFiles = new File("D:\\parsing\\error");
    private String format[] = {"XML"};
    private SessionFactory sessionFactory;
    private EntryDao entryDao;
    private ExecutorService executorService;
    private HandlerFiles handlerFiles = new HandlerFilesImpl();


    public Setting(int pool) {
        this.sessionFactory = buildSessionFactory();
        this.entryDao = new EntryDaoImpl(this.sessionFactory);
        this.executorService = Executors.newFixedThreadPool(pool);
    }


    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public File getTreatedFiles() {
        return treatedFiles;
    }

    public void setTreatedFiles(File treatedFiles) {
        this.treatedFiles = treatedFiles;
    }

    public File getErrorFiles() {
        return errorFiles;
    }

    public void setErrorFiles(File errorFiles) {
        this.errorFiles = errorFiles;
    }

    public String[] getFormat() {
        return format;
    }

    public void setFormat(String[] format) {
        this.format = format;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public EntryDao getEntryDao() {
        return entryDao;
    }

    public void setEntryDao(EntryDao entryDao) {
        this.entryDao = entryDao;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public HandlerFiles getHandlerFiles() {
        return handlerFiles;
    }

    public void setHandlerFiles(HandlerFiles handlerFiles) {
        this.handlerFiles = handlerFiles;
    }

    public File getDirMonitoring() {
        return dirMonitoring;
    }

    public void setDirMonitoring(File dirMonitoring) {
        this.dirMonitoring = dirMonitoring;
    }
}
