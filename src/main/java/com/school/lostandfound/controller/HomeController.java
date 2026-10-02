package com.school.lostandfound.controller;

import com.school.lostandfound.model.Claim;
import com.school.lostandfound.model.Item;
import com.school.lostandfound.model.Message;
import com.school.lostandfound.model.User;
import com.school.lostandfound.repository.ClaimRepository;
import com.school.lostandfound.repository.ItemRepository;
import com.school.lostandfound.repository.MessageRepository;
import com.school.lostandfound.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ClaimRepository claimRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository,
                          ItemRepository itemRepository,
                          ClaimRepository claimRepository,
                          MessageRepository messageRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.claimRepository = claimRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Item> recentItems = itemRepository.findAll().stream()
            .sorted(Comparator.comparing(Item::getDateReported).reversed())
            .limit(4)
            .collect(Collectors.toList());

        model.addAttribute("recentItems", recentItems);
        model.addAttribute("totalItems", itemRepository.count());
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalClaims", claimRepository.count());
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent() ||
            userRepository.findByUsername(user.getUsername()).isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "A user with that email or username already exists.");
            return "redirect:/register";
        }

        user.setRole(User.Role.STUDENT);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please log in.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User currentUser = getCurrentUser(principal);
        List<Item> items = itemRepository.findAll();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("items", items);
        model.addAttribute("lostCount", items.stream().filter(i -> i.getItemType() == Item.ItemType.LOST).count());
        model.addAttribute("foundCount", items.stream().filter(i -> i.getItemType() == Item.ItemType.FOUND).count());
        model.addAttribute("claimCount", claimRepository.count());
        return "dashboard";
    }

    @GetMapping("/report-lost")
    public String reportLostPage(Model model, Principal principal) {
        model.addAttribute("item", new Item());
        model.addAttribute("currentUser", getCurrentUser(principal));
        return "report-lost";
    }

    @PostMapping("/report-lost")
    public String reportLost(@ModelAttribute("item") Item item,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                            Principal principal,
                            RedirectAttributes redirectAttributes) throws IOException {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            return "redirect:/login";
        }

        item.setCreatedBy(currentUser);
        item.setItemType(Item.ItemType.LOST);
        item.setStatus(Item.Status.OPEN);
        saveImage(item, imageFile);
        itemRepository.save(item);

        redirectAttributes.addFlashAttribute("successMessage", "Lost item reported successfully.");
        return "redirect:/my-reports";
    }

    @GetMapping("/report-found")
    public String reportFoundPage(Model model, Principal principal) {
        model.addAttribute("item", new Item());
        model.addAttribute("currentUser", getCurrentUser(principal));
        return "report-found";
    }

    @PostMapping("/report-found")
    public String reportFound(@ModelAttribute("item") Item item,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             Principal principal,
                             RedirectAttributes redirectAttributes) throws IOException {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            return "redirect:/login";
        }

        item.setCreatedBy(currentUser);
        item.setItemType(Item.ItemType.FOUND);
        item.setStatus(Item.Status.OPEN);
        saveImage(item, imageFile);
        itemRepository.save(item);

        redirectAttributes.addFlashAttribute("successMessage", "Found item reported successfully.");
        return "redirect:/my-reports";
    }

    @GetMapping("/lost-items")
    public String lostItems(@RequestParam(value = "keyword", required = false) String keyword,
                           @RequestParam(value = "category", required = false) String category,
                           @RequestParam(value = "location", required = false) String location,
                           Model model) {
        List<Item> items = itemRepository.findAll().stream()
            .filter(item -> item.getItemType() == Item.ItemType.LOST)
            .filter(item -> matchesFilter(item, keyword, category, location))
            .collect(Collectors.toList());

        model.addAttribute("items", items);
        return "lost-items";
    }

    @GetMapping("/found-items")
    public String foundItems(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "category", required = false) String category,
                            @RequestParam(value = "location", required = false) String location,
                            Model model) {
        List<Item> items = itemRepository.findAll().stream()
            .filter(item -> item.getItemType() == Item.ItemType.FOUND)
            .filter(item -> matchesFilter(item, keyword, category, location))
            .collect(Collectors.toList());

        model.addAttribute("items", items);
        return "found-items";
    }

    @GetMapping("/my-reports")
    public String myReports(Model model, Principal principal) {
        User currentUser = getCurrentUser(principal);
        List<Item> items = itemRepository.findAll().stream()
            .filter(item -> currentUser != null && item.getCreatedBy() != null && item.getCreatedBy().getId().equals(currentUser.getId()))
            .collect(Collectors.toList());

        model.addAttribute("items", items);
        model.addAttribute("currentUser", currentUser);
        return "my-reports";
    }

    @GetMapping("/my-claims")
    public String myClaims(Model model, Principal principal) {
        User currentUser = getCurrentUser(principal);
        List<Claim> claims = claimRepository.findAll().stream()
            .filter(claim -> currentUser != null && claim.getClaimant() != null && claim.getClaimant().getId().equals(currentUser.getId()))
            .collect(Collectors.toList());

        model.addAttribute("claims", claims);
        model.addAttribute("currentUser", currentUser);
        return "my-claims";
    }

    @GetMapping("/messages")
    public String messages(Model model, Principal principal) {
        User currentUser = getCurrentUser(principal);
        List<Message> messages = messageRepository.findAll().stream()
            .filter(message -> currentUser != null &&
                ((message.getSender() != null && message.getSender().getId().equals(currentUser.getId())) ||
                 (message.getReceiver() != null && message.getReceiver().getId().equals(currentUser.getId()))))
            .sorted(Comparator.comparing(Message::getSentAt))
            .collect(Collectors.toList());

        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", currentUser);
        return "messages";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("claims", claimRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/approve/{claimId}")
    public String approveClaim(@PathVariable Long claimId, RedirectAttributes redirectAttributes) {
        Claim claim = claimRepository.findById(claimId).orElse(null);
        if (claim != null) {
            claim.setStatus(Claim.Status.APPROVED);
            Item item = claim.getItem();
            if (item != null) {
                item.setStatus(Item.Status.RETURNED);
                itemRepository.save(item);
            }
            claimRepository.save(claim);
            redirectAttributes.addFlashAttribute("successMessage", "Claim approved successfully.");
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/reject/{claimId}")
    public String rejectClaim(@PathVariable Long claimId, RedirectAttributes redirectAttributes) {
        Claim claim = claimRepository.findById(claimId).orElse(null);
        if (claim != null) {
            claim.setStatus(Claim.Status.REJECTED);
            claimRepository.save(claim);
            redirectAttributes.addFlashAttribute("successMessage", "Claim rejected successfully.");
        }
        return "redirect:/admin";
    }

    @GetMapping("/items/{id}")
    public String itemDetails(@PathVariable Long id, Model model, Principal principal) {
        Item item = itemRepository.findById(id).orElseThrow();
        model.addAttribute("item", item);
        model.addAttribute("currentUser", getCurrentUser(principal));
        model.addAttribute("receiverId",
            item.getCreatedBy() != null ? item.getCreatedBy().getId() : (item.getOwner() != null ? item.getOwner().getId() : item.getFinder().getId()));
        return "item-details";
    }

    @PostMapping("/claim")
    public String submitClaim(@RequestParam Long itemId,
                              @RequestParam String notes,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            return "redirect:/login";
        }

        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Item not found.");
            return "redirect:/lost-items";
        }

        Claim claim = new Claim();
        claim.setItem(item);
        claim.setClaimant(currentUser);
        claim.setNotes(notes);
        claim.setStatus(Claim.Status.PENDING);
        claimRepository.save(claim);

        redirectAttributes.addFlashAttribute("successMessage", "Your claim has been submitted successfully.");
        return "redirect:/items/" + itemId;
    }

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam Long itemId,
                             @RequestParam Long receiverId,
                             @RequestParam String content,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser(principal);
        if (currentUser == null) {
            return "redirect:/login";
        }

        User receiver = userRepository.findById(receiverId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        if (receiver == null || item == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unable to send message.");
            return "redirect:/lost-items";
        }

        Message message = new Message();
        message.setSender(currentUser);
        message.setReceiver(receiver);
        message.setItem(item);
        message.setContent(content);
        messageRepository.save(message);

        redirectAttributes.addFlashAttribute("successMessage", "Message sent successfully.");
        return "redirect:/items/" + itemId;
    }

    private User getCurrentUser(Principal principal) {
        if (principal == null) {
            return null;
        }
        return userRepository.findByUsername(principal.getName()).orElse(null);
    }

    private boolean matchesFilter(Item item, String keyword, String category, String location) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();
        String normalizedCategory = category == null ? "" : category.trim().toLowerCase();
        String normalizedLocation = location == null ? "" : location.trim().toLowerCase();

        boolean byKeyword = normalizedKeyword.isEmpty() ||
            item.getTitle().toLowerCase().contains(normalizedKeyword) ||
            item.getDescription().toLowerCase().contains(normalizedKeyword) ||
            item.getCategory().toLowerCase().contains(normalizedKeyword) ||
            item.getLocationFound().toLowerCase().contains(normalizedKeyword);

        boolean byCategory = normalizedCategory.isEmpty() || item.getCategory().toLowerCase().contains(normalizedCategory);
        boolean byLocation = normalizedLocation.isEmpty() || (item.getLocationFound() != null && item.getLocationFound().toLowerCase().contains(normalizedLocation));

        return byKeyword && byCategory && byLocation;
    }

    private void saveImage(Item item, MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return;
        }

        Path uploadDir = Paths.get("src/main/resources/static/uploads");
        Files.createDirectories(uploadDir);

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename().replaceAll("\\s+", "_");
        Path target = uploadDir.resolve(fileName);
        Files.copy(imageFile.getInputStream(), target);

        item.setImageUrl("/uploads/" + fileName);
    }
}
