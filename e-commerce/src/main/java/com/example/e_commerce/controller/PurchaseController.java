package com.example.e_commerce.controller;
import com.example.e_commerce.model.Purchase;
import com.example.e_commerce.repository.PurchaseRepository;
import com.example.e_commerce.util.XssSanitizer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseRepository repo;

    private static final Set<String> ALLOWED_TIMES = Set.of("10 AM", "11 AM", "12 PM");
    private static final Set<String> PRODUCTS = Set.of("Product A", "Product B", "Product C");
    private static final Set<String> DISTRICTS = Set.of("Colombo", "Kandy", "Galle", "Jaffna"); // example

    public PurchaseController(PurchaseRepository repo) {
        this.repo = repo;
    }

    // Return profile info from JWT claims
    @GetMapping("/me")
    public Map<String, Object> getProfile(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> profile = new HashMap<>();
        profile.put("username", extractUsername(jwt));
        profile.put("name", jwt.getClaimAsString("name"));
        profile.put("email", jwt.getClaimAsString("email"));
        profile.put("phone_number", jwt.getClaimAsString("phone_number"));
        profile.put("country", jwt.getClaimAsString("country"));
        return profile;
    }

    // Create purchase for authenticated user
    @PostMapping
    public ResponseEntity<?> createPurchase(@AuthenticationPrincipal Jwt jwt,
                                            @Valid @RequestBody Purchase payload) {

        String username = extractUsername(jwt);

        // Validate product
        if (!PRODUCTS.contains(payload.getProductName())) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid product"));
        }

        // validate date (on or after today and not Sunday)
        LocalDate today = LocalDate.now();
        if (payload.getPurchaseDate().isBefore(today)) {
            return ResponseEntity.badRequest().body(Map.of("error", "purchase date cannot be before today"));
        }
        if (payload.getPurchaseDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
            return ResponseEntity.badRequest().body(Map.of("error", "purchase date cannot be on Sunday"));
        }

        // validate delivery time
        if (!ALLOWED_TIMES.contains(payload.getDeliveryTime())) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid delivery time"));
        }

        // validate district
        if (!DISTRICTS.contains(payload.getDeliveryLocation())) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid delivery location"));
        }

        // quantity validated by @Min

        // sanitize message
        String safeMessage = XssSanitizer.sanitize(payload.getMessage());
        payload.setMessage(safeMessage);

        // set username (ignore any username sent by client)
        payload.setUsername(username);

        Purchase saved = repo.save(payload);
        return ResponseEntity.ok(saved);
    }

    // Get all purchases for the authenticated user
    @GetMapping
    public List<Purchase> getMyPurchases(@AuthenticationPrincipal Jwt jwt) {
        String username = extractUsername(jwt);
        return repo.findByUsernameOrderByPurchaseDateDesc(username);
    }

    // Helper to extract a stable username from JWT: prefer `preferred_username` then `sub`
    private String extractUsername(Jwt jwt) {
        String preferred = jwt.getClaimAsString("preferred_username");
        if (preferred != null && !preferred.isBlank()) return preferred;
        String email = jwt.getClaimAsString("email");
        if (email != null && !email.isBlank()) return email;
        return jwt.getSubject(); // fallback to sub
    }
}
