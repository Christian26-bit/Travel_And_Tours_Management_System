# Travel Management System - UML Diagrams

This document contains the structural representation of the Travel Management System using UML Class Diagrams.

## 1. Core System Architecture (MVC & Repositories)

This diagram shows how the Models, Repositories, Controllers, and View Factories interact.

```mermaid
classDiagram
    namespace Models {
        class Person {
            <<abstract>>
            #StringProperty name
            #StringProperty email
            #StringProperty contactNumber
        }

        class Client {
            -IntegerProperty clientId
            -StringProperty address
            -StringProperty customerType
        }

        class Employee {
            -IntegerProperty employeeId
            -StringProperty password
            -BooleanProperty isManager
        }

        class Booking {
            -IntegerProperty bookingId
            -StringProperty status
            -ObjectProperty<LocalDate> bookingDate
            -int paxCount
        }
        
        class BookingData {
            <<DTO>>
            -IntegerProperty selectedPackageId
            -IntegerProperty selectedHotelId
            -BooleanProperty termsAccepted
            +calculateTotalPrice() int
        }

        class TourPackage {
            -IntegerProperty packageId
            -StringProperty packageName
            -IntegerProperty price
            -StringProperty destination
        }
    }

    namespace Repositories {
        class BookingRepositoryImpl {
            +getRecentBookings() List~Booking~
            +createBooking(BookingData) boolean
        }
        class TourPackageRepositoryImpl {
            +getTourPackages() List~TourPackage~
        }
    }

    namespace Controllers {
        class BookingsController {
            -TableView<Booking> booking_table
        }
        class TourPackagesController {
            -List<TourPackage> packagesList
        }
        class BookingWizardControllers {
            -BookingData bookingData
        }
    }

    %% Relationships
    Person <|-- Client : extends
    Person <|-- Employee : extends
    
    Booking --> Client : linked to
    Booking --> TourPackage : contains
    
    BookingRepositoryImpl ..> Booking : generates
    BookingRepositoryImpl ..> BookingData : persists from
    
    BookingsController --> Booking : displays
    TourPackagesController --> TourPackage : displays
    BookingWizardControllers --> BookingData : modifies
```

## 2. Model Usage Breakdown

### Person Hierarchy
- **Person (Abstract):** Base class providing common identity fields (Name, Email, Contact).
- **Client:** Extends Person; represents the customer and includes travel history/address.
- **Employee:** Extends Person; represents staff members with login credentials and roles.

### Booking System
- **Booking:** The persistent record of a finalized transaction. Used in dashboard tables and history views.
- **BookingData (DTO):** A "Data Transfer Object" that exists only during the **Booking Wizard** process. It holds temporary state (selected hotel, vehicle, etc.) before it is converted into a permanent `Booking` and `Client` record in the database.

### Product Catalog
- **TourPackage:** Represents the core travel products. It is used by the User to browse destinations and by the Admin to manage pricing and availability.
