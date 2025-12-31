# Project Plan: CHT Travel and Tours

**Role:** Project Manager / Scrum Master
**Goal:** Deliver a professional JavaFX Desktop App and PHP Web App with feature parity.
**Current Focus:** JavaFX "New Booking" Feature.

## 🚨 Critical Blockers
- **[BLOCKER] Database Schema:** The development team (you) has not provided the database schema. We cannot write Models or SQL queries without knowing table names and columns.
- **[MISSING] Entity Models:** No POJOs exist for `Client`, `Package`, `Booking`.

## Phase 1: Foundation (JavaFX)
*Building the backbone so the app doesn't collapse.*

- [ ] **Task 1.1: Define Database Schema** (User Input Required)
    - Need schemas for: Customers, Packages, Bookings, Payments.
- [ ] **Task 1.2: Create Entity Models**
    - Create `Client.java`
    - Create `Package.java` (or `Trip.java`)
    - Create `Booking.java`
- [ ] **Task 1.3: Data Access Layer**
    - Create methods in `Model.java` (or separate DAOs) to fetch/save these entities.

## Phase 2: New Booking Feature (JavaFX)
*Implementing the "Add Booking" user story.*

- [ ] **Task 2.1: Design UI (`NewBooking-view.fxml`)**
    - [ ] Client Selection (Search/Dropdown)
    - [ ] Package Selection (Dropdown/Table)
    - [ ] Date Picker (Travel Date)
    - [ ] Pax Count (Number of travelers)
    - [ ] Total Price Calculation (Auto-update)
    - [ ] Submit Button
- [ ] **Task 2.2: Controller Logic (`AddBookingController.java`)**
    - [ ] `initialize()`: Populate Client and Package lists.
    - [ ] `calculateTotal()`: Logic to multiply price * pax.
    - [ ] `saveBooking()`: Validate inputs and insert into DB.

## Phase 3: Web Parity (PHP)
*To be planned after JavaFX feature complete.*

---
**Notes:**
- Code must be clean. No raw SQL in Controllers if possible (use Model/DAO).
- Naming conventions: CamelCase for Java classes, camelCase for variables.
