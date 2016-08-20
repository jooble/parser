package service;

import IO.HandlerFiles;
import IO.HandlerFilesImpl;
import dao.EntryDao;
import dao.EntryDaoImpl;
import model.Entry;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Setting {
    private File dirMonitoring;
    private File treatedFiles;
    private File errorFiles;
    private String formats[];
    private SessionFactory sessionFactory;
    private EntryDao entryDao;
    private ExecutorService executorService;
    private HandlerFiles handlerFiles = new HandlerFilesImpl();
    private int poolSize;
    private File validationXsd;


    public Setting() {
        this.sessionFactory = buildSessionFactory();
        this.entryDao = new EntryDaoImpl(this.sessionFactory);
        buildConfiguration();
        this.executorService = Executors.newFixedThreadPool(poolSize);
    }


    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();

            Metadata metadata = new MetadataSources(standardRegistry)
                    .addAnnotatedClass(Entry.class)
                    .addAnnotatedClassName("model.Entry")
                    .getMetadataBuilder()
                    .build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private void buildConfiguration() {
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            property.load(fis);

            this.dirMonitoring = new File(property.getProperty("dir.monitoring"));
            this.errorFiles = new File(property.getProperty("dir.errorFiles"));
            this.treatedFiles = new File(property.getProperty("dir.treatedFiles"));
            this.validationXsd = new File(property.getProperty("validation.xsd"));
            this.poolSize = Integer.parseInt(property.getProperty("pool.size"));
            this.formats = property.getProperty("formats").split("#");
        } catch (IOException e) {
            System.out.println("ОШИБКА: Файл свойств отсуствует!");
        }
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

    public String[] getFormats() {
        return formats;
    }

    public void setFormats(String[] formats) {
        this.formats = formats;
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

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public File getValidationXsd() {
        return validationXsd;
    }

    public void setValidationXsd(File validationXsd) {
        this.validationXsd = validationXsd;
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
