package IO;

import java.io.File;
import java.util.List;

public interface HandlerFiles {

    List<File> checkDirectory(File dir, String... format);

    void moveFile(File file, File dir);

}

