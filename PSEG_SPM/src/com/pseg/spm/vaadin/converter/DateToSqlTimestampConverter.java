package com.pseg.spm.vaadin.converter;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import com.vaadin.data.util.converter.Converter;

/**
 * This version will be added into Vaadin 7.2. Delete this when Vaadin 7.2 is
 * released. This is for Oracle database
 * 
 * @author TDD13
 * 
 */

@SuppressWarnings("serial")
public class DateToSqlTimestampConverter implements Converter<Date, java.sql.Timestamp> {

	private static final Logger LOGGER = Logger.getLogger(DateToSqlTimestampConverter.class.getName());

	@Override
	public java.sql.Timestamp convertToModel(Date value, Class<? extends java.sql.Timestamp> targetType, Locale locale) throws ConversionException {

		if (targetType != getModelType()) {
			LOGGER.warning("Converter only supports " + getModelType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return null;

		return new java.sql.Timestamp(value.getTime());
	}

	@Override
	public Date convertToPresentation(java.sql.Timestamp value, Class<? extends Date> targetType, Locale locale) throws ConversionException {

		if (targetType != getPresentationType()) {
			LOGGER.warning("Converter only supports " + getPresentationType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return null;

		return new Date(value.getTime());
	}

	@Override
	public Class<java.sql.Timestamp> getModelType() {
		return java.sql.Timestamp.class;
	}

	@Override
	public Class<Date> getPresentationType() {
		return Date.class;
	}

}
