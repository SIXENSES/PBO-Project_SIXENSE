import java.util.ArrayList;
import java.util.Scanner;

class Menu {
    String name;
    String category;
    double price;

    Menu(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }
}

public class CafeOrderingSystem {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Menu> menuList = new ArrayList<>();

    public static void main(String[] args) {

        // Data menu awal
        menuList.add(new Menu("Pisang Keju", "Makanan", 10000.0));
        menuList.add(new Menu("French Fries", "Makanan", 15000.0));
        menuList.add(new Menu("Spaghetti", "Makanan", 28000.0));
        menuList.add(new Menu("Iced Coffee", "Minuman", 18000.0));
        menuList.add(new Menu("Cappuccino", "Minuman", 22000.0));
        menuList.add(new Menu("Coffee Latte", "Minuman", 22000.0));
        menuList.add(new Menu("Matcha Latte", "Minuman", 20000.0));
        menuList.add(new Menu("Thai Tea", "Minuman", 22000.0));

        int choice;

        do {
            showMainMenu();

            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // membersihkan buffer

            switch (choice) {
                case 1 -> displayMenu();
                case 2 -> editMenu();
                case 3 -> deleteMenu();
                case 4 -> searchMenu();
                case 5 -> orderMenu();
                case 6 -> System.out.println("\nThank you for using Cafe Ordering System!");
                default -> System.out.println("\nInvalid choice!");
            }

        } while (choice != 6);
    }

    // Main Menu
    static void showMainMenu() {

        System.out.println("\n======================================");
        System.out.println("        CAFE ORDERING SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Display Menu");
        System.out.println("2. Edit Menu");
        System.out.println("3. Delete Menu");
        System.out.println("4. Search Menu");
        System.out.println("5. Order Menu");
        System.out.println("6. Exit");
        System.out.println("======================================");
    }

    // Display Menu
    static void displayMenu() {

        System.out.println("\n========== MENU KOPI ==========");

        if (menuList.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        for (int i = 0; i < menuList.size(); i++) {

            Menu menu = menuList.get(i);

            System.out.println(
                (i + 1) + ". " +
                menu.name +
                " | " +
                menu.category +
                " | Rp" +
                menu.price
            );
        }
    }

    // Edit Menu
    static void editMenu() {

        displayMenu();

        System.out.print("\nEnter menu number to edit: ");
        int number = input.nextInt();
        input.nextLine();

        if (number < 1 || number > menuList.size()) {
            System.out.println("Invalid menu number!");
            return;
        }

        Menu menu = menuList.get(number - 1);

        System.out.print("New menu name : ");
        menu.name = input.nextLine();

        System.out.print("New category  : ");
        menu.category = input.nextLine();

        System.out.print("New price     : Rp");
        menu.price = input.nextDouble();
        input.nextLine();

        System.out.println("\nMenu successfully updated!");
    }

    // Delete Menu
    static void deleteMenu() {

        displayMenu();

        System.out.print("\nEnter menu number to delete: ");
        int number = input.nextInt();
        input.nextLine();

        if (number < 1 || number > menuList.size()) {
            System.out.println("Invalid menu number!");
            return;
        }

        menuList.remove(number - 1);

        System.out.println("\nMenu successfully deleted!");
    }

    // Search Menu
    static void searchMenu() {

        System.out.println("\n========== CARI MENU ==========");

        System.out.print("Masukan nama menu: ");
        String keyword = input.nextLine();

        boolean found = false;

        for (Menu menu : menuList) {

            if (menu.name.toLowerCase()
                    .contains(keyword.toLowerCase())) {

                System.out.println(
                    menu.name +
                    " | " +
                    menu.category +
                    " | Rp" +
                    menu.price
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("Menu tidak ditemukan.");
        }
    }

    // Order Menu
    static void orderMenu() {

        System.out.println("\n========== ORDER MENU ==========");
        System.out.print("Masukan nama Anda: ");
        String customerName = input.nextLine();

        double grandTotal = 0;
        ArrayList<String> orderDetails = new ArrayList<>();
        String addMore = "";

        do {
            System.out.println("\n--- Pilih Kategori ---");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            System.out.print("Pilih kategori pesanan (1/2): ");
            int categoryChoice = input.nextInt();
            input.nextLine();

            String selectedCategory = switch (categoryChoice) {
                case 1 -> "Makanan";
                case 2 -> "Minuman";
                default -> {
                    System.out.println("Kategori tidak valid!");
                    yield "";
                }
            };

            if (selectedCategory.isEmpty()) {
                continue; // Kembali ke awal perulangan pesanan jika salah pilih
            }

            // Membuat list sementara khusus untuk kategori yang dipilih
            ArrayList<Menu> filteredMenu = new ArrayList<>();
            for (Menu menu : menuList) {
                if (menu.category.equalsIgnoreCase(selectedCategory)) {
                    filteredMenu.add(menu);
                }
            }

            if (filteredMenu.isEmpty()) {
                System.out.println("\nMaaf, tidak ada menu tersedia di kategori " + selectedCategory);
                continue;
            }

            // Menampilkan menu berdasarkan kategori
            System.out.println("\n--- Daftar " + selectedCategory + " ---");
            for (int i = 0; i < filteredMenu.size(); i++) {
                Menu menu = filteredMenu.get(i);
                System.out.println((i + 1) + ". " + menu.name + " | Rp" + menu.price);
            }

            System.out.print("\nEnter menu number: ");
            int number = input.nextInt();

            if (number < 1 || number > filteredMenu.size()) {
                System.out.println("Invalid menu number!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = input.nextInt();
            input.nextLine(); // membersihkan buffer

            // Mengambil data menu dari list yang sudah disaring
            Menu menu = filteredMenu.get(number - 1);
            double subTotal = menu.price * quantity;
            
            // Tambahkan subtotal ke total keseluruhan
            grandTotal += subTotal;
            
            // Simpan detail pesanan ini ke dalam list
            orderDetails.add("- " + menu.name + " (" + quantity + "x) : Rp" + subTotal);

            // Menanyakan apakah ingin menambah pesanan (misal: tambah minuman)
            System.out.print("\nApakah Anda ingin menambahkan pesanan lain? (ya/tidak): ");
            addMore = input.nextLine();

        } while (addMore.equalsIgnoreCase("y"));

        // Menampilkan Struk Akhir
        System.out.println("\n========== ORDER DETAILS ==========");
        System.out.println("Nama Pemesan : " + customerName);
        System.out.println("Daftar Pesanan:");
        for (String detail : orderDetails) {
            System.out.println(detail);
        }
        System.out.println("-----------------------------------");
        System.out.println("TOTAL KESELURUHAN : Rp" + grandTotal);
        System.out.println("===================================");

        System.out.println("Pesanan berhasil dibuat!");
    }
}