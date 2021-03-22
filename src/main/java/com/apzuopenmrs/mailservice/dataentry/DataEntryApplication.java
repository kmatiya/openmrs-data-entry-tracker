package com.apzuopenmrs.mailservice.dataentry;

import com.apzuopenmrs.mailservice.dataentry.mail.AppointmentEmailGenerator;
import com.apzuopenmrs.mailservice.dataentry.mail.AppointmentReportService;
import com.apzuopenmrs.mailservice.dataentry.mail.EmailServiceImpl;
import com.apzuopenmrs.mailservice.dataentry.mail.FacilityAggregator;
import com.apzuopenmrs.mailservice.dataentry.mail.FacilityCount;
import com.apzuopenmrs.mailservice.dataentry.model.AppointmentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.mail.MessagingException;
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

	public static void main(String[] args) throws MessagingException {
		ApplicationContext context  = SpringApplication.run(DataEntryApplication.class, args);
		DataEntryApplication app = context.getBean(DataEntryApplication.class);
	//	app.emailServiceImpl.sendSimpleMessage("khormatiya@gmail.com","Test Spring","<h1>Hello</h1>" +
	//			"<p style='color:red'>Red</p>");
		String dataEntryDate = "2021-03-16";
		HashMap<String, List<AppointmentReport>> dailyAppointments = app.appointmentReportService.getUpperNenoAppointmentReport(dataEntryDate);
		if(dailyAppointments != null){
			System.out.println("Reports collected");
			List<FacilityCount> facilityCounts = new ArrayList<>();
			System.out.println("Aggregating reports.");
			for (String location : dailyAppointments.keySet()) {
				FacilityCount facilityCount = app.getFacilityAggregator().getAggregatedData(location,dailyAppointments.get(location));
				facilityCounts.add(facilityCount);
			}
			System.out.println("Preparing to send emails.");
			String message = app.appointmentEmailGenerator.generateAppointmentEmail(facilityCounts,dataEntryDate);
			app.emailServiceImpl.sendSimpleMessage("khormatiya@gmail.com","Daily Data Entry for Upper Neno "+ dataEntryDate, message);
			//	app.emailServiceImpl.sendSimpleMessage("lthengo@pih.org","Daily Data Entry for Lower Neno "+ dataEntryDate, message);
			//app.emailServiceImpl.sendSimpleMessage("amahaka@pih.org", "Daily Data Entry for Upper Neno "+ dataEntryDate, message);
			//app.emailServiceImpl.sendSimpleMessage("lthengo@pih.org", "Daily Data Entry for Upper Neno "+ dataEntryDate, message);
			//app.emailServiceImpl.sendSimpleMessage("gmalunga@pih.org","Daily Data Entry for Upper Neno "+ dataEntryDate, message);
			//app.emailServiceImpl.sendSimpleMessage("cgoliath@pih.org","Daily Data Entry for Upper Neno "+ dataEntryDate, message);
			System.out.println("Appointment email sent");
		}
		else{
			System.out.println("Appointment not generated");
		}
	}
}