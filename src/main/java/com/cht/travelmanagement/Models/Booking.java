package com.cht.travelmanagement.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class Booking {
	private final IntegerProperty bookingId;
	private final IntegerProperty employeeId;
	private final IntegerProperty clientId;
	private final IntegerProperty packageId;
	private final ObjectProperty<LocalDate> bookingDate;
	private final StringProperty status;
	private final IntegerProperty paxCount; ;
	
	public Booking(int bookingId, int employeeId,int clientId, int packageId, LocalDate bookingDate, String status, int paxCount) {
		this.bookingId = new SimpleIntegerProperty(this, "bookingId", bookingId);
		this.employeeId = new SimpleIntegerProperty(this, "employeeId", employeeId);
		this.clientId = new SimpleIntegerProperty(this, "clientId", clientId);
		this.packageId = new SimpleIntegerProperty(this, "packageId", packageId);
		this.bookingDate = new SimpleObjectProperty<>(this, "bookingDate", bookingDate);
		this.status = new SimpleStringProperty(this, "isConfirmed", status);
		this.paxCount = new SimpleIntegerProperty(this, "paxCount", paxCount);
	}
	
	public int getBookingId() { return bookingId.get(); }
	public IntegerProperty bookingIdProperty() { return bookingId;}
	
	public int getEmployeeId() { return employeeId.get(); }
	public IntegerProperty employeeIdProperty() { return employeeId; }
	
	public int getClientId() { return clientId.get(); }
	public IntegerProperty clientIdProperty() { return clientId; }
	
	public int getPackageId() { return packageId.get(); }
	public IntegerProperty packageIdProperty() { return packageId; }
	
	public LocalDate getBookingDate() { return bookingDate.get(); }
	public ObjectProperty<LocalDate> bookingDateProperty() { return bookingDate; }
	
	public String getStatus() { return status.get(); }
	public StringProperty statusProperty() { return status; }
	
	public int getPaxCount() { return paxCount.get(); }
	public IntegerProperty paxCountProperty() { return paxCount; }
	
	
	
}
