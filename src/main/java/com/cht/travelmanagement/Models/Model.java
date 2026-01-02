package com.cht.travelmanagement.Models;

import com.cht.travelmanagement.Models.Repository.BookingRepository;
import com.cht.travelmanagement.Models.Repository.ClientRepository;
import com.cht.travelmanagement.Models.Repository.Implementation.BookingRepositoryImpl;
import com.cht.travelmanagement.Models.Repository.Implementation.ClientRepositoryImpl;
import com.cht.travelmanagement.View.AccountType;
import com.cht.travelmanagement.View.AdminViewFactory;
import com.cht.travelmanagement.View.UserViewFactory;
import com.cht.travelmanagement.View.ViewFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
	private static Model model;
	private final ViewFactory viewFactory;
	private final DatabaseDriver databaseDriver;
	private final AdminViewFactory adminViewFactory;
	private final UserViewFactory userViewFactory;

	public final ObservableList<TourPackage> tourPackages;
	public final ObservableList<Booking> bookings;

	private boolean userLoggedInSuccessfully;

	private Model() {
		this.viewFactory = new ViewFactory();
		this.databaseDriver = new DatabaseDriver();
		this.adminViewFactory = new AdminViewFactory();
		this.userViewFactory = new UserViewFactory();
		this.userLoggedInSuccessfully = false;

		this.tourPackages = FXCollections.observableArrayList();
		this.bookings = FXCollections.observableArrayList();

	}

	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	public AdminViewFactory getAdminViewFactory() {
		return adminViewFactory;
	}

	public UserViewFactory getUserViewFactory() {
		return userViewFactory;
	}

	/**
	 * User Methods Section
	 */
	public void setUserLoggedInSuccessfully(boolean status) {
		this.userLoggedInSuccessfully = status;
	}

	public boolean getUserLoggedInSuccessfully() {
		return this.userLoggedInSuccessfully;
	}

	/**
	 * Evaluate login credentials
	 * @param email
	 * @param password
	 * @param accountType
	 */
	public void evaluateLoginCredentials(String email, String password, AccountType accountType) {
		String verifyLogin = "SELECT COUNT(1) FROM employee WHERE email = ? AND  password = ?";

		if (accountType == AccountType.ADMIN) {
			verifyLogin += " AND isManager = 1";
		}
		try (Connection connection = DatabaseDriver.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(verifyLogin);) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					if (resultSet.getInt(1) == 1) {
						this.userLoggedInSuccessfully = true;
					} else {
						System.out.println("Invalid Login. Please try again.");
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Get Clients from Database
     */
	public ObservableList<Client> getClients() {
		ClientRepository clientRepository = new ClientRepositoryImpl();
		return clientRepository.getClients();
	}

	/** 
	 *  Get Dashboard Data from Database
	 */
	public int[] getDashboardData() {
		BookingRepository bookingRepository = new BookingRepositoryImpl();
		int clientCount = getClients().size();
		return bookingRepository.getDashboardData(clientCount);
	}
	

	/**
	 * Get Tour Packages from Database
     */
	public ObservableList<TourPackage> getTourPackages() {
		String query = "SELECT * FROM package";
		tourPackages.clear();
		try (Connection connection = DatabaseDriver.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			
			while (resultSet.next()) {
				int packageId = resultSet.getInt("PackageId");
				String packageName = resultSet.getString("Name");
				String description = resultSet.getString("Description");
				String destination = resultSet.getString("Destination");
				int durationDays = resultSet.getInt("Duration");
				int maxParticipants = resultSet.getInt("MaxPax");
				String inclusions = resultSet.getString("Inclusions");
				int price = resultSet.getInt("Price");
				boolean isActive = resultSet.getBoolean("IsActive");
				int createdBy = resultSet.getInt("CreatedByEmployeeId");

				TourPackage tourPackage = new TourPackage(packageId, packageName, description, destination,
						durationDays, maxParticipants, inclusions, price, isActive, createdBy);

				tourPackages.add(tourPackage);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);

		}
		return tourPackages;
	}

	public ObservableList<Booking> getRecentBookings() {
		BookingRepository bookingRepository = new BookingRepositoryImpl();
		return bookingRepository.getRecentBookings();
	}


	public ObservableList<Booking> getAllBookings() {
		BookingRepository bookingRepository = new BookingRepositoryImpl();
		return bookingRepository.getAllBookings();
	}
}
