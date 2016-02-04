package com.codespace.googleapps.calendar.controller;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codespace.googleapps.calendar.service.CalendarService;
import com.google.api.services.calendar.model.Event;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CalendarController.class);

	@Autowired
	CalendarService calendarService;
	
	@RequestMapping(value="/hello", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String hello() {
		LOGGER.debug("Services Controller");
		return "Hello!";
	}
	
	@RequestMapping(value="/events", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Event> getEvents() {
		GregorianCalendar gcal = new GregorianCalendar();
		gcal.set(2017, 1, 1);
		return calendarService.getAvailability("kalyan.boosetty@gmail.com", new Date(), gcal.getTime());
	}
	
	@RequestMapping(value="/oauth2callback", method = RequestMethod.GET)
	@ResponseBody
	public String callback() {
		return "Authentication successful!";
	}
	
}
