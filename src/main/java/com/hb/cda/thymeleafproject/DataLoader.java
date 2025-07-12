package com.hb.cda.thymeleafproject;

import com.hb.cda.thymeleafproject.entity.Product;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.ProductRepository;
import com.hb.cda.thymeleafproject.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class DataLoader implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DataLoader.class);
    }
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(productRepository.count() == 0 && userRepository.count() == 0 ) {
            Product tv = new Product("Smart TV 55 pouces", 899.99, "Téléviseur intelligent haute définition.", 10);
            Product laptop = new Product("Ordinateur Portable Gamer", 1499.00, "Ordinateur puissant pour le jeu vidéo.", 5);
            Product novel = new Product("Le Grand Voyage", 15.50, "Un roman d'aventure captivant.", 50);
            Product oven = new Product("Four Électrique Moderne", 450.00, "Four encastrable avec plusieurs fonctions de cuisson.", 8);
            Product shirt = new Product("T-Shirt en Coton", 25.00, "T-shirt confortable en coton biologique.", 100);
            Product headphones = new Product("Casque Audio sans Fil", 129.99, "Casque Bluetooth avec réduction de bruit active.", 30);
            Product coffeeMaker = new Product("Machine à Café Expresso", 299.50, "Machine à expresso automatique avec broyeur intégré.", 12);
            Product fitnessTracker = new Product("Bracelet Connecté Fitness", 75.00, "Moniteur d'activité avec capteur de fréquence cardiaque.", 40);
            Product blender = new Product("Mixeur Puissant", 85.00, "Mixeur de cuisine idéal pour smoothies et soupes.", 25);
            Product bookshelf = new Product("Étagère Murale Design", 60.00, "Étagère flottante moderne pour livres et décorations.", 15);
            Product drone = new Product("Drone avec Caméra 4K", 750.00, "Drone pliable avec caméra haute résolution pour la photographie aérienne.", 7);
            Product backpack = new Product("Sac à Dos de Voyage", 95.00, "Sac à dos ergonomique et résistant pour les voyages.", 20);
            productRepository.saveAll(List.of(
                    tv, laptop, novel, oven, shirt,
                    headphones, coffeeMaker, fitnessTracker, blender, bookshelf, drone, backpack
            ));
            System.out.println("Produits crées dans BDD.");

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setRole("ROLE_ADMIN"); // Rôle ADMIN


            User user1 = new User();
            user1.setUsername("alice");
            user1.setPassword(passwordEncoder.encode("alice"));
            user1.setRole("ROLE_USER");

            User user2 = new User();
            user2.setUsername("bob");
            user2.setPassword(passwordEncoder.encode("bob"));
            user2.setRole("ROLE_USER");

            User user3 = new User();
            user3.setUsername("charlie");
            user3.setPassword(passwordEncoder.encode("charlie"));
            user3.setRole("ROLE_USER");

            userRepository.saveAll(List.of(adminUser, user1, user2, user3));
            System.out.println("Utilisateurs créés dans BDD.");
        } else {
            System.out.println("La base de donnée dispose déja de données.");
        }
    }
}
