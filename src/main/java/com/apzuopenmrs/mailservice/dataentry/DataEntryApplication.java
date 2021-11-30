package com.apzuopenmrs.mailservice.dataentry;

import com.apzuopenmrs.mailservice.dataentry.mail.AppointmentEmailGenerator;
import com.apzuopenmrs.mailservice.dataentry.mail.AppointmentReportService;
import com.apzuopenmrs.mailservice.dataentry.mail.EmailServiceImpl;
import com.apzuopenmrs.mailservice.dataentry.mail.FacilityAggregator;
import com.apzuopenmrs.mailservice.dataentry.mail.FacilityCount;
import com.apzuopenmrs.mailservice.dataentry.model.AppointmentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class DataEntryApplication {

	public EmailServiceImpl getEmailServiceImpl() {
		return emailServiceImpl;
	}

	public AppointmentReportService getAppointmentReportService() {
		return appointmentReportService;
	}

	public FacilityAggregator getFacilityAggregator() {
		return facilityAggregator;
	}

	public AppointmentEmailGenerator getAppointmentEmailGenerator(){ return appointmentEmailGenerator;}

	@Autowired
	EmailServiceImpl emailServiceImpl;

	@Autowired
	FacilityAggregator facilityAggregator;

	@Autowired
	AppointmentReportService appointmentReportService;

	@Autowired
	AppointmentEmailGenerator appointmentEmailGenerator;

	@Value("${emr_username}")
	private String username;

	@Value("${emr_password}")
	private String password;

	@Value("${neno_server_location}")
	private String serverLocation;

	@Value("${date_of_entry}")
	private String dateOfEntry;

	@Value("${recipient_email}")
	private String recipientEmails;

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Value("${spring.mail.password}")
	private String senderPassword;

	@Value("${use_configured_date_of_entry}")
	private boolean autoConfiguredDateOfEntry;


	public static void main(String[] args) throws MessagingException {
		ApplicationContext context  = SpringApplication.run(DataEntryApplication.class, args);
		DataEntryApplication app = context.getBean(DataEntryApplication.class);
		String dataEntryDate = "";

		if(app.autoConfiguredDateOfEntry) {
			dataEntryDate = app.dateOfEntry;
		}
		else{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataEntryDate=LocalDate.now().format(formatter);
		}
		HashMap<String, List<AppointmentReport>> dailyAppointments = app.appointmentReportService.getAppointmentReport(dataEntryDate, app.username, app.password, app.serverLocation);
		if(dailyAppointments != null){
			System.out.println("Reports collected");
			String[] recipients = app.recipientEmails.split(",");
			List<FacilityCount> facilityCounts = new ArrayList<>();
			System.out.println("Aggregating reports.");
			for (String location : dailyAppointments.keySet()) {
				FacilityCount facilityCount = app.getFacilityAggregator().getAggregatedData(location,dailyAppointments.get(location));
				facilityCounts.add(facilityCount);
			}
			System.out.println("Preparing to send emails.");
			String message = app.appointmentEmailGenerator.generateAppointmentEmail(facilityCounts,dataEntryDate);
			app.emailServiceImpl.sendSimpleMessage(recipients, "Daily Data Entry for "+ app.serverLocation+" Neno"+ dataEntryDate, message, app.senderEmail);
			System.out.println("Appointment email sent");
		}
		else{
			System.out.println("Appointment not generated");
		}
	}
}