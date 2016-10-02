package com.pseg.spm.vaadin.converter;

import java.util.Locale;
import java.util.logging.Logger;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class BooleanToStringConverter implements Converter<Boolean, String> {

	private static final Logger LOGGER = Logger.getLogger(BooleanToStringConverter.class.getName());

	@Override
	public String convertToModel(Boolean value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		if (targetType != getModelType()) {
			LOGGER.warning("Converter only supports " + getModelType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return null;

		if (value == true)
			return "Y";
		else
			return "N";
	}

	@Override
	public Boolean convertToPresentation(String value, Class<? extends Boolean> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		if (targetType != getPresentationType()) {
			LOGGER.warning("Converter only supports " + getPresentationType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return null;

		if ("Y".equals(value))
			return true;
		else if ("N".equals(value))
			return false;

		return null;
	}

	@Override
	public Class<String> getModelType() {
		return String.class;
	}

	@Override
	public Class<Boolean> getPresentationType() {
		return Boolean.class;
	}

}
