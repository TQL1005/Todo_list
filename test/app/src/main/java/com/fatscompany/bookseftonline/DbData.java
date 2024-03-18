package com.fatscompany.bookseftonline;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.fatscompany.bookseftonline.DAO.BookDAO;
import com.fatscompany.bookseftonline.DAO.CategoryDAO;
import com.fatscompany.bookseftonline.DAO.InventoryDAO;
import com.fatscompany.bookseftonline.DAO.OrderDetailDAO;
import com.fatscompany.bookseftonline.DAO.PublishersDAO;
import com.fatscompany.bookseftonline.DAO.SaleOrderDAO;
import com.fatscompany.bookseftonline.DAO.UserDAO;
import com.fatscompany.bookseftonline.Entitis.Book;
import com.fatscompany.bookseftonline.Entitis.Category;
import com.fatscompany.bookseftonline.Entitis.Inventory;
import com.fatscompany.bookseftonline.Entitis.OrderDetail;
import com.fatscompany.bookseftonline.Entitis.Publishers;
import com.fatscompany.bookseftonline.Entitis.SaleOrder;
import com.fatscompany.bookseftonline.Entitis.User;
import com.fatscompany.bookseftonline.databinding.ActivityDbDataBinding;
import com.fatscompany.bookseftonline.databinding.ActivitySignUpAppBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DbData extends AppCompatActivity {
    ActivityDbDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDbDataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        List<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Bob");
        nameList.add("Charlie");
        nameList.add("David");
        nameList.add("Eva");
        // Thêm nhiều tên khác vào danh sách
        Random random = new Random();
        int randomYear = random.nextInt(2023 - 1990 + 1) + 1990;
        Executor executor = Executors.newSingleThreadExecutor();
        int randomIndex = random.nextInt(nameList.size());

        String randomName = nameList.get(randomIndex);
        int minSold = 10; // Giá trị tối thiểu
        int maxSold = 1000; // Giá trị tối đa
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras eu metus vitae turpis venenatis aliquam quis vel tortor. Integer vestibulum mollis mattis. Sed ut iaculis dui, non maximus nunc. Ut ut quam non sem semper finibus. Fusce eget sodales sem. Vivamus tempus erat tortor, ut pulvinar eros semper nec. Nulla pharetra risus quis lorem tristique, vitae lobortis neque ornare. Vivamus et lobortis nunc, sed tincidunt sem.\n" + "\n" + "Morbi consectetur blandit ligula, eu posuere nunc. Mauris id semper massa. Morbi sit amet semper sapien. Suspendisse placerat neque et ornare tempus. Nullam at mauris mollis, accumsan ex iaculis, blandit erat. Etiam eu porttitor tortor, a tempus dui. Nunc eu arcu ut sapien cursus vestibulum sed eget quam. Suspendisse eu nunc pellentesque, maximus sem quis, sollicitudin purus. Donec et suscipit ligula, ac aliquet elit. Vivamus est dui, fermentum et porttitor quis, posuere vitae est.";
        double minPrice = 10.0; // Giá trị tối thiểu
        double maxPrice = 50.0; // Giá trị tối đa

        binding.btnClick.setOnClickListener(v -> {

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase database = AppDatabase.getInstance(DbData.this);

                    database.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            insertInitialData();
                        }
                    });
                }
            });


        });

        binding.btnCreat.setOnClickListener(v1 -> {
            List<String> titles = Arrays.asList(
                    "All the Beauty in the World",
                    "Van Gogh's Cypresses",
                    "Adult Coloring Book",
                    "MoMA Now",
                    "Burning Man: Art on Fire",
                    "An Indigenous Present",
                    "The Monuments Men",
                    "National Gallery of Art 12-Month 2024",
                    "The Louvre: All the Paintings",
                    "Black American Portraits",
                    "Edward Hopper's New York",
                    "Karl Lagerfeld: A Line of Beauty",
                    "In America: A Lexicon of Fashion",
                    "Hokusai’s Landscapes",
                    "Another World",
                    "Great Paintings That Tell Stories",
                    "Van Gogh and the Avant-Garde",
                    "Women of Abstract Expressionism",
                    "Sargent and Spain",
                    "The Art of Patrick Carlson",
                    "Morbid Curiosities",
                    "Mazes for Kids 4-8",
                    "Good Faeries/Bad Faeries",
                    "Edvard Munch: Trembling Earth",
                    "Deep in the Dimension Of Pitchforks and Horns",
                    "Smithsonian Engagement Calendar 2024",
                    "Just Above Midtown",
                    "YouTube 1 Million Subscriber Play Button",
                    "Hermes: Heavenly Days",
                    "The Art Museum Journal",
                    "A Movement in Every Direction",
                    "J. Pierpont Morgan’s Library: Building a",
                    "Chromatopia",
                    "Faithful and Fearless",
                    "Philip Guston Now",
                    "Alice Neel: People Come First",
                    "Bauhaus Typography at 100",
                    "Ugly’s Electrical References, 2023 Edition",
                    "Where Is the Amazon?",
                    "Where the Lost Wander",
                    "The Bookstore on the Beach",
                    "Things We Never Got Over (Knockemout)",
                    "The Silent Patient",
                    "Where the Crawdads Sing",
                    "Verity",
                    "Love and Other Words",
                    "Password Book",
                    "It’s Not Summer Without You",
                    "Lessons in Chemistry",
                    "Demon Copperhead",
                    "The Woman in Me",
                    "Single AF Cocktails",
                    "Surviving College",
                    "Find Your Passion",
                    "Watching You",
                    "Spooktacular Jokes and Riddles for Kids 8-12",
                    "It Will All Work Out",
                    "The Scenic Route",
                    "Reminders of Him",
                    "The Pale-Faced Lie",
                    "The Girl Who Was Taken",
                    "White Lies",
                    "The Covenant of Water",
                    "The Love of My Life",
                    "The Butcher and the Wren"
            );

            List<String> images;
            images = Arrays.asList(
                    "https://images-na.ssl-images-amazon.com/images/I/71jP6wzY9LL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71LMPBv3V1L._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71K3tyPzOeL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61EJ3sVyt9L._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/81fTNWriZKL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71paeMkAfBL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/51o-6riS3jL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71nob5M82pL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/91Kzg9U3ifL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71gyao4tTDL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61OVceZUR-L._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/51MQod3XleL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71khW8zWO9L._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/81lkrHI8MOL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/611Kpe4+4-L._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/81ksfEaEayL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61SDgs9I4zL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/91T9lsfXolL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61-OiOBNsrL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71+mWT5+PhL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61Td+V2b9kL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71IalN2kfXL._AC_UL600_SR600,400_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/81tLe+YZx5L._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61SblEAn09L._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/71060BvePUL._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61SoiEJ+p6L._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/711ENZoWPwL._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/41lYIWnSSbL._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61fUBY8mLvL._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61JUi+INc-L._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61yuonbmB7L._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/61AZjrfYCOL._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/41JQSlc0-CL._AC_UL300_SR300,200_.jpg",
                    "https://images-na.ssl-images-amazon.com/images/I/51zsbESbYbL._AC_UL600_SR600,400_.jpg",
                    "https://m.media-amazon.com/images/I/812z90AMmXL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91oj+0Fh8ES._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/81WOdv0GnZS._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71HsLc-TNlL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91BbLCJOruL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91pW1-pwF0L._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/917iVfhEhxL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/719CLtj6ndL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71I2tnZ2B8L._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/81sNNdPiKQL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71bNmUDdM0L._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91eIanmV7KL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/61KI7oL-u9L._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91r4+mZbTVL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71pQsSolcEL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71-vS+CfnwL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71HrsLqoMJL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/61paLzfsEIL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71ZCvkVlq9L._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/81PxkaGqNkL._AC_UL480_FMwebp_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/61ctngWorgL._AC_UL480_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/61ASAHIZC0L._AC_UL480_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/71XAm8STjSL._AC_UL480_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/61z5u-7YZKL._AC_UL480_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91b7tm523VL._AC_UL480_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/91BfSqEcRdL._AC_UL480_QL65_.jpg",
                    "https://m.media-amazon.com/images/I/81Y9XuluekL._AC"
            );

            int randomSold = random.nextInt(maxSold - minSold + 1) + minSold;
            double randomPrice = random.nextDouble() * (maxPrice - minPrice) + minPrice;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase database = AppDatabase.getInstance(DbData.this);

                    database.runInTransaction(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < 60; i++) {
                                int randomCategory = (int) (Math.random() * 10) + 1; // Random category between 1 and 10
                                int randomPublisher = (int) (Math.random() * 5) + 1; // Random publisher between 1 and 5
                                Book book = new Book(titles.get(i), description, randomPrice, // Random price between 10 and 60
                                        "Random Author", randomYear, true, randomCategory, // Random category
                                        randomPublisher, // Random publisher
                                        images.get(i), (int) (Math.random() * 100) // Random sold between 0 and 100
                                );
                                if (database.bookDao().checkBookExist(book.getTitle()) == null) {
                                    database.bookDao().insert(book);
                                }
                            }

                        }
                    });
                }
            });

            binding.eTitle.setText("");
            binding.eImage.setText("");

        });
    }

    private void insertInitialData() {
        AppDatabase database = AppDatabase.getInstance(DbData.this);

        //USER
        User user1 = new User("user1", "pass1", "First 1", "Last 1", "email1@example.com", "123456789", false, "CUSTOMER");
        User user2 = new User("user2", "pass2", "First 2", "Last 2", "email2@example.com", "987654321", false, "CUSTOMER");
        User user3 = new User("1", "1", "First 2", "Last 2", "email2@example.com", "987654321", false, "CUSTOMER");
        User user4 = new User("USERTEST", "2", "First 2", "Last 2", "email2@example.com", "987654321", false, "CUSTOMER");
        User user5 = new User("admin", "1", "First 2", "Last 2", "email2@example.com", "987654321", false, "ADMIN");

        UserDAO userDao = database.userDao();
        userDao.insert(user1, user2, user3, user4, user5);


        CategoryDAO cateDao = database.categoryDao();
        List<Category> categories = new ArrayList<>();

        categories.add(new Category("Empty", "Empty"));
        categories.add(new Category("Science Fiction & Fantasy", "Books about science and technology"));
        categories.add(new Category("Literature", "Books about literature and novels"));
        categories.add(new Category("History", "Books about history and politics"));
        categories.add(new Category("Cooking", "Books with cooking and culinary instructions"));
        categories.add(new Category("Psychology", "Books about psychology and personal development"));
        categories.add(new Category("Business", "Books about business and management"));
        categories.add(new Category("Mystery", "Mystery and thriller books"));
        categories.add(new Category("Medicine", "Books about medicine and health"));
        categories.add(new Category("Children", "Books for children and young adults"));
        categories.add(new Category("Religion", "Books about religion and spirituality"));

        for (Category cate : categories) {
            Category cateCheck = cateDao.checkCateExits(cate.getName());
            if (cateCheck == null) {
                cateDao.insert(cate);
            }
        }
        ;


        PublishersDAO pb = database.publishersDao();
        List<Publishers> publishers = new ArrayList<>();
        publishers.add(new Publishers("Penguin Books"));
        publishers.add(new Publishers("HarperCollins"));
        publishers.add(new Publishers("Random House"));
        publishers.add(new Publishers("Simon & Schuster"));
        publishers.add(new Publishers("Macmillan Publishers"));

        for (Publishers cate : publishers) {
            Publishers pubCheck = pb.checkPublisherExits(cate.getName());
            if (pubCheck == null) {
                pb.insertPublisher(cate);
            }
        }
        ;

      /*  InventoryDAO ivo = database.inventoryDao();
       ivo.insertInventory(new Inventory(10, 1));
        ivo.insertInventory(new Inventory(23, 2));
       ivo.insertInventory(new Inventory(33, 3));*/

        //        InventoryDAO ivo = database.inventoryDao();
//        ivo.insertInventory(new Inventory(10, 1));
//        ivo.insertInventory(new Inventory(23, 2));
//        ivo.insertInventory(new Inventory(33, 3));

       SaleOrderDAO sod = database.saleOrderDao();
       sod.insertSaleOrder(new SaleOrder(new Date(), 1, "2323" ));
       sod.insertSaleOrder(new SaleOrder(new Date(), 2, "2323"));
       sod.insertSaleOrder(new SaleOrder(new Date(), 1, "0909"));

      OrderDetailDAO ord = database.orderDetailDao();
       ord.insertOrderDetail(new OrderDetail("Order 1", 1, 1, 5));
        ord.insertOrderDetail(new OrderDetail("Order 2", 2, 1, 3));
       ord.insertOrderDetail(new OrderDetail("Order 3", 3, 2, 2));
    }

}