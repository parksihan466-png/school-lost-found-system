package com.school.lostandfound.config;

import com.school.lostandfound.model.Claim;
import com.school.lostandfound.model.Item;
import com.school.lostandfound.model.User;
import com.school.lostandfound.repository.ClaimRepository;
import com.school.lostandfound.repository.ItemRepository;
import com.school.lostandfound.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ClaimRepository claimRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      ItemRepository itemRepository,
                      ClaimRepository claimRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.claimRepository = claimRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setFullName("System Administrator");
            admin.setEmail("admin@school.edu");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("aisha").isEmpty()) {
            User aisha = new User();
            aisha.setFullName("Aisha Khan");
            aisha.setEmail("aisha@school.edu");
            aisha.setUsername("aisha");
            aisha.setPassword(passwordEncoder.encode("student123"));
            aisha.setRole(User.Role.STUDENT);
            userRepository.save(aisha);
        }

        if (userRepository.findByUsername("michael").isEmpty()) {
            User michael = new User();
            michael.setFullName("Michael Lee");
            michael.setEmail("michael@school.edu");
            michael.setUsername("michael");
            michael.setPassword(passwordEncoder.encode("student123"));
            michael.setRole(User.Role.STUDENT);
            userRepository.save(michael);
        }

        if (userRepository.findByUsername("sarah").isEmpty()) {
            User sarah = new User();
            sarah.setFullName("Sarah Brown");
            sarah.setEmail("sarah@school.edu");
            sarah.setUsername("sarah");
            sarah.setPassword(passwordEncoder.encode("staff123"));
            sarah.setRole(User.Role.STAFF);
            userRepository.save(sarah);
        }

        if (itemRepository.count() == 0) {
            User admin = userRepository.findByUsername("admin").orElseThrow();
            User aisha = userRepository.findByUsername("aisha").orElseThrow();
            User michael = userRepository.findByUsername("michael").orElseThrow();
            User sarah = userRepository.findByUsername("sarah").orElseThrow();

            Item wallet = new Item();
            wallet.setTitle("Black Wallet");
            wallet.setDescription("Leather wallet with student ID and cash inside.");
            wallet.setCategory("Accessories");
            wallet.setItemType(Item.ItemType.LOST);
            wallet.setLocationFound("Library Study Hall");
            wallet.setStatus(Item.Status.OPEN);
            wallet.setImageUrl("https://images.unsplash.com/photo-1627123424574-724758594e93?auto=format&fit=crop&w=800&q=80");
            wallet.setOwner(aisha);
            wallet.setCreatedBy(aisha);
            itemRepository.save(wallet);

            Item bottle = new Item();
            bottle.setTitle("Blue Water Bottle");
            bottle.setDescription("Insulated bottle with school logo.");
            bottle.setCategory("Personal Items");
            bottle.setItemType(Item.ItemType.FOUND);
            bottle.setLocationFound("Biology Lab");
            bottle.setStatus(Item.Status.OPEN);
            bottle.setImageUrl("https://images.unsplash.com/photo-1602143407151-7111542de6e8?auto=format&fit=crop&w=800&q=80");
            bottle.setFinder(michael);
            bottle.setCreatedBy(michael);
            itemRepository.save(bottle);

            Item notebook = new Item();
            notebook.setTitle("Science Notebook");
            notebook.setDescription("Notebook with chemistry notes and name label.");
            notebook.setCategory("Stationery");
            notebook.setItemType(Item.ItemType.LOST);
            notebook.setLocationFound("Main Corridor");
            notebook.setStatus(Item.Status.OPEN);
            notebook.setImageUrl("https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=800&q=80");
            notebook.setOwner(sarah);
            notebook.setCreatedBy(sarah);
            itemRepository.save(notebook);

            Item keychain = new Item();
            keychain.setTitle("Silver Keychain");
            keychain.setDescription("Silver keychain with a house key and tag.");
            keychain.setCategory("Accessories");
            keychain.setItemType(Item.ItemType.FOUND);
            keychain.setLocationFound("Gym Entrance");
            keychain.setStatus(Item.Status.OPEN);
            keychain.setImageUrl("https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=800&q=80");
            keychain.setFinder(admin);
            keychain.setCreatedBy(admin);
            itemRepository.save(keychain);
        }

        if (claimRepository.count() == 0) {
            User aisha = userRepository.findByUsername("aisha").orElseThrow();
            User michael = userRepository.findByUsername("michael").orElseThrow();
            Item wallet = itemRepository.findAll().stream().filter(item -> item.getTitle().equals("Black Wallet")).findFirst().orElseThrow();
            Item keychain = itemRepository.findAll().stream().filter(item -> item.getTitle().equals("Silver Keychain")).findFirst().orElseThrow();

            Claim claim1 = new Claim();
            claim1.setItem(wallet);
            claim1.setClaimant(michael);
            claim1.setNotes("I believe this is mine. I can provide my student ID details.");
            claim1.setStatus(Claim.Status.PENDING);
            claimRepository.save(claim1);

            Claim claim2 = new Claim();
            claim2.setItem(keychain);
            claim2.setClaimant(aisha);
            claim2.setNotes("This keychain matches my set of keys.");
            claim2.setStatus(Claim.Status.PENDING);
            claimRepository.save(claim2);
        }
    }
}
