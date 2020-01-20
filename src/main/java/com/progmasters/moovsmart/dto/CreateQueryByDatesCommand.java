package com.progmasters.moovsmart.dto;

import java.time.LocalDateTime;

public class CreateQueryByDatesCommand {


    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    public CreateQueryByDatesCommand() {
    }

    public CreateQueryByDatesCommand(LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }
}
