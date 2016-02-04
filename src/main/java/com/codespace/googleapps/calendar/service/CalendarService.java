package com.codespace.googleapps.calendar.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codespace.googleapps.auth.ClientAuthorization;
import com.codespace.googleapps.util.ExceptionHandler;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

@Service
public class CalendarService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(CalendarService.class);

	@Autowired
	private ClientAuthorization clientAuthorization;

	public void pushNotifications() {

	}

	// email, start time, end time
	public List<Event> getAvailability(String email, Date startDate, Date endDate) {

		List<Event> items = Collections.emptyList();

		try {
			com.google.api.services.calendar.Calendar service = clientAuthorization
					.getService();

			Events events = service.events().list(email)
					.setTimeMin(new DateTime(startDate))
					.setTimeMax(new DateTime(endDate)).setOrderBy("startTime")
					.setSingleEvents(true).execute();

			items = events.getItems();

			if (items.size() != 0) {
				LOGGER.debug("Upcoming events");
				for (Event event : items) {
					DateTime eventStartTime = event.getStart().getDateTime();
					if (eventStartTime == null) {
						eventStartTime = event.getStart().getDate();
					}
					LOGGER.debug("{%s (%s)}\n", event.getSummary(),
							eventStartTime);
				}
			} else {
				LOGGER.debug("No upcoming events found.");
			}

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
		}

		return items;
	}

	public void syncCalendar() {

	}

}
