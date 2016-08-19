import service.EntryService;
import service.Setting;

public class Main {
    public static void main(String[] args) {
        Setting setting = new Setting();
        EntryService service = new EntryService(setting);

        service.run();
        service.shutdown();
    }
}
