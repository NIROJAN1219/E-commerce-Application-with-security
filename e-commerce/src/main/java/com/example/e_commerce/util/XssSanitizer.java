package com.example.e_commerce.util;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class XssSanitizer {
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public static String sanitize(String input) {
        if (input == null) return null;
        return POLICY.sanitize(input);
    }
}
