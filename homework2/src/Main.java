public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        Console console = new Console(database);
        console.run();
    }
}
