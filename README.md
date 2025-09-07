# Create README.md file with the provided content

readme_content = """
# 🛒 Secure Product Purchase Web Application  
A full-stack web application built with **React**, **Spring Boot**, and **Auth0** that allows authenticated users to purchase products, view their orders, and manage their profile securely. The app follows **OWASP Top 10 best practices** and uses **OIDC-based authentication** for robust security.

---

## 🚀 Features  
- 🔑 **Auth0 Authentication (OIDC)**  
  - Secure login/logout flow using Auth0’s Universal Login.  
  - Access tokens for API authorization.  

- 👤 **User Profile Management**  
  - Display authenticated user’s profile (username, name, email, contact, country).  

- 🛍️ **Product Purchase**  
  - Purchase form with:  
    - Authenticated username (from ID token).  
    - Delivery date selection (future dates only, excluding Sundays).  
    - Preferred delivery time (10 AM, 11 AM, 12 PM).  
    - Delivery location (district list).  
    - Product name (from predefined list).  
    - Quantity & message.  

- 📦 **Order History**  
  - View past purchases and upcoming deliveries.  

- 🔐 **Security Best Practices**  
  - JWT validation with Auth0 JWKS.  
  - CSRF protection using `CookieCsrfTokenRepository`.  
  - Content Security Policy (CSP) headers to mitigate XSS.  
  - Role-based access control: users can only access their own orders.  
  - Parameterized queries via Spring Data JPA to prevent SQL Injection.  

---

## 🛠️ Tech Stack  
| Layer            | Technology                          |
|------------------|------------------------------------|
| Frontend         | React, Axios, Auth0 React SDK      |
| Backend          | Spring Boot, Spring Security, JPA  |
| Database         | PostgreSQL (or MySQL)              |
| Authentication   | Auth0 (OIDC)                       |
| Build Tools      | Maven, npm                         |
| Hosting/HTTPS    | Configurable via `application.properties` |

---



## ⚙️ Setup Instructions  

### 🔹 Prerequisites  
- Java 17+  
- Node.js & npm  
- PostgreSQL (or MySQL)  
- Auth0 account  

---


### 🔹 Backend Setup  
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-username/secure-product-purchase.git
   cd secure-product-purchase/backend


Configure application.properties:

Always show details
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/securedb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

auth0.audience=https://dev-tr0s621i58jqwl3a.us.auth0.com/api/v2/
auth0.issuer=https://your-tenant.auth0.com/


Build and run:

Always show details
mvn clean install
mvn spring-boot:run

🔹 Frontend Setup

Go to the frontend folder:

Always show details
cd ../frontend


Install dependencies:

Always show details
npm install


Configure Auth0 in .env:

Always show details
REACT_APP_AUTH0_DOMAIN=dev-tr0s621i58jqwl3a.us.auth0.com
REACT_APP_AUTH0_CLIENT_ID=DSbIRd4MOKoZ3AMFZMcjQjO8D2j360mt
REACT_APP_AUTH0_AUDIENCE=https://dev-tr0s621i58jqwl3a.us.auth0.com/api/v2/


Start the app:

Always show details
npm start

🔐 Security Highlights
Vulnerability	Mitigation Approach
XSS	CSP headers, React default escaping, input validation.
CSRF	Spring Security’s CookieCsrfTokenRepository.
SQL Injection	Spring Data JPA parameterized queries.
Broken Auth	OIDC with Auth0; RS256-signed tokens.
Sensitive Data	HTTPS-only deployment; no sensitive data in tokens.
🧪 Testing

Tested authentication flow using Auth0 Universal Login.

Validated token decoding with jwt.io.

Verified security headers with browser DevTools.

Performed manual checks for XSS, CSRF, and SQL Injection.

📖 Learning Outcomes

Learned to integrate OIDC with Auth0 in a full-stack app.

Gained hands-on experience with Spring Security token validation.

Practiced OWASP Top 10 security best practices in a real project.

Built a scalable React + Spring Boot app with RBAC.
