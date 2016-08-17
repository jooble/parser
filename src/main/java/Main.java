import service.EntryService;
import service.Setting;

public class Main {
    public static void main(String[] args) throws Exception {
        Setting setting = new Setting(10);

        EntryService service = new EntryService(setting);

        service.run();
        service.shutdown();
    }
}
