package ar.edu.utn.frba.dds.converters;

import java.time.Period;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PeriodConverter implements AttributeConverter<Period, String> {
    @Override
    public String convertToDatabaseColumn(Period period) {
        return period != null ? period.toString() : null;
    }

    @Override
    public Period convertToEntityAttribute(String dbData) {
        return dbData != null ? Period.parse(dbData) : null;
    }
}
