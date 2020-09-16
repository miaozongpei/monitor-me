package com.m.beyond.view.page.forms;

import com.m.beyond.view.page.AbstractElement;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

@Setter
@Getter
public class DateTimePicker extends AbstractElement {

    private String defaultDate= DateFormatUtils.ISO_DATE_FORMAT.format(new Date());
    private String defaultTime=DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(new Date());
    private String dateTimeValueName;
    private String dateTimeDValueName;
    public DateTimePicker(String dateTimeDValueName) {
        this.dateTimeDValueName=dateTimeDValueName;
    }
    public DateTimePicker(String defaultDate, String defaultTime) {
        this.defaultDate = defaultDate;
        this.defaultTime=defaultTime;
    }
}
