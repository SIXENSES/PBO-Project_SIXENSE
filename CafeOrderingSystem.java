import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
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

    // Membuat formatter khusus untuk format angka Indonesia (pemisah ribuan menggunakan titik)
    static String formatRupiah(double nominal) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        return formatter.format(nominal);
    }

    public static void main(String[] args) {

        // Data semua menu
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
                case 2 -> searchMenu();
                case 3 -> orderMenu();
                case 4 -> System.out.println("\nTerima kasih telah memesan di Cafe kami!");
                default -> System.out.println("\nInvalid choice!");
            }

        } while (choice != 4);
    }

    // Tampilan Menu Utama
    static void showMainMenu() {

        System.out.println("\n======================================");
        System.out.println("        CAFE ORDERING SYSTEM");
        System.out.println("======================================");
        System.out.println("1. Display Menu");
        System.out.println("2. Search Menu");
        System.out.println("3. Order Menu");
        System.out.println("4. Exit");
        System.out.println("======================================");
    }

    // Tampilan Menu
    static void displayMenu() {

        System.out.println("\n========== ALL MENU ==========");

        if (menuList.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        for (int i = 0; i < menuList.size(); i++) {

            Menu menu = menuList.get(i);

            // Menggunakan formatRupiah di sini
            System.out.println(
                (i + 1) + ". " +
                menu.name +
                " | " +
                menu.category +
                " | Rp" +
                formatRupiah(menu.price)
            );
        }
    }

    // Tampilan Pencarian Menu
    static void searchMenu() {

        System.out.println("\n========== CARI MENU ==========");

        System.out.print("Masukan nama menu: ");
        String keyword = input.nextLine();

        boolean found = false;

        for (Menu menu : menuList) {

            if (menu.name.toLowerCase()
                    .contains(keyword.toLowerCase())) {

                // Menggunakan formatRupiah di sini
                System.out.println(
                    menu.name +
                    " | " +
                    menu.category +
                    " | Rp" +
                    formatRupiah(menu.price)
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("Menu tidak ditemukan.");
        }
    }

    // Tampilan Order Menu
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
                continue; 
            }

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

            System.out.println("\n--- Daftar " + selectedCategory + " ---");
            for (int i = 0; i < filteredMenu.size(); i++) {
                Menu menu = filteredMenu.get(i);
                // Menggunakan formatRupiah di sini
                System.out.println((i + 1) + ". " + menu.name + " | Rp" + formatRupiah(menu.price));
            }

            System.out.print("\nEnter menu number: ");
            int number = input.nextInt();

            if (number < 1 || number > filteredMenu.size()) {
                System.out.println("Invalid menu number!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = input.nextInt();
            input.nextLine(); 

            Menu menu = filteredMenu.get(number - 1);
            double subTotal = menu.price * quantity;
            
            grandTotal += subTotal;
            
            // Menggunakan formatRupiah untuk subTotal
            orderDetails.add("- " + menu.name + " (" + quantity + "x) : Rp" + formatRupiah(subTotal));

            System.out.print("\nApakah Anda ingin menambahkan pesanan lain? (ya/tidak): ");
            addMore = input.nextLine();

        } while (addMore.equalsIgnoreCase("ya") || addMore.equalsIgnoreCase("y"));

        System.out.println("\n========== ORDER DETAILS ==========");
        System.out.println("Nama Pemesan : " + customerName);
        System.out.println("Daftar Pesanan:");
        for (String detail : orderDetails) {
            System.out.println(detail);
        }
        System.out.println("-----------------------------------");
        // Menggunakan format Rupiah untuk grandTotal
        System.out.println("TOTAL KESELURUHAN : Rp" + formatRupiah(grandTotal));
        System.out.println("===================================");

        System.out.println("Pesanan berhasil dibuat!");
    }
}
