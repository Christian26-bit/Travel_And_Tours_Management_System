# EMPLOYEE

EmployeeID (PK)
Name
Email (unique)
Password
ContactNumber
IsManager (boolean)
IsActive (boolean)

# CLIENT

ClientID (PK)
Name
Email (Unique)
Address
ContactNumber
CustomerType
DateRegistered

# BOOKING

BookingID (PK)
EmployeeID (FK)
ClientID (FK)
PackageID (FK)
BookingDate
Status (pending, confirmed, cancelled)
PaxCount

# PAYMENT

PaymentID (PK)
BookingID (FK)
Amount
PaymentDate
Method (cash, card, bank transfer, gcash)
Status (pending, paid, refunded)
ReferenceNumber

# PACKAGE

PackageID (PK)
Name
Description
Destination
Duration (number of days)
MaxPax
Inclusions (short text)
Price
IsActive
CreatedByEmployeeID (FK)

# PACKAGE_TRIPS

PackageTripID (PK)
PackageID (FK)
TripID (FK)
Sequence (order in itinerary)
DayOfPackage

# TRIP

TripID (PK)
Name
Description

Location
StartDate
EndDate
IsActive (boolean)

# TRIP_ACTIVITIES

TripActivityID (PK)
TripID (FK)
ActivityID (FK)
Location
StartDateTime
EndDateTime
GuideName
EquipmentProvided
IsIncluded

# ACTIVITY

ActivityID (PK)
Name
Description
DefaultLocation
DefaultDuration
IsIncludedByDefault (boolean, optional)

# TRIP_VEHICLES

TripVehicleID (PK)
TripID (FK)
VehicleID (FK)
DepartureLocation
ArrivalLocation
DepartureDateTime
ArrivalDateTime
SeatNumber
TicketReference
IsIncluded

# VEHICLE

VehicleID (PK)
Type (bus, van, boat, plane)
Capacity
PlateNumber
ProviderName

# TRIP_ACCOMMODATIONS

TripAccommodationID (PK)
TripID (FK)
AccommodationID (FK)
CheckInDate
CheckOutDate
RoomType
IsIncluded

# ACCOMMODATION

AccommodationID (PK)
Name
Address
Contact
Amenities
NumberOfRooms
DefaultRoomType