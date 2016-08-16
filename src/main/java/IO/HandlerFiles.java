package IO;

import java.io.File;
import java.util.List;

public interface HandlerFiles {

    //TODO выбрать что будет возвращать
    List<File> checkDirectory(File dir, String format);

    void moveFile(File file, File dir);

}

