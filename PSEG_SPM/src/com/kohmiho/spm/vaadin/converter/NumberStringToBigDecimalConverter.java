package com.kohmiho.spm.vaadin.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Logger;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class NumberStringToBigDecimalConverter implements Converter<String, BigDecimal> {

	private static final Logger LOGGER = Logger.getLogger(NumberStringToBigDecimalConverter.class.getName());

	private NumberFormat nf;

	public NumberStringToBigDecimalConverter(NumberFormat nf) {
		this.nf = nf;
	}

	public NumberFormat getNumberFormat() {
		return nf;
	}

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		if (targetType != getModelType()) {
			LOGGER.warning("Converter only supports " + getModelType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value || "".equals(value))
			return null;

		Number parse = 0;
		try {
			parse = nf.parse(value);
		} catch (ParseException e) {
			LOGGER.warning(e.toString());
			return null;
		}

		return new BigDecimal(parse.doubleValue());
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		if (targetType != getPresentationType()) {
			LOGGER.warning("Converter only supports " + getPresentationType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return null;

		return nf.format(value);
	}

	@Override
	public Class<BigDecimal> getModelType() {
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
