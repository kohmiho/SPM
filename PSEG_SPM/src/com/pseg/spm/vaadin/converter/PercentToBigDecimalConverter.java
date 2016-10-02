package com.pseg.spm.vaadin.converter;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.logging.Logger;

import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class PercentToBigDecimalConverter implements Converter<Double, BigDecimal> {

	private static final Logger LOGGER = Logger.getLogger(NumberStringToBigDecimalConverter.class.getName());

	@Override
	public BigDecimal convertToModel(Double value, Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		if (targetType != getModelType()) {
			LOGGER.warning("Converter only supports " + getModelType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return new BigDecimal(0);

		return new BigDecimal(value / 100);
	}

	@Override
	public Double convertToPresentation(BigDecimal value, Class<? extends Double> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {

		if (targetType != getPresentationType()) {
			LOGGER.warning("Converter only supports " + getPresentationType().getName() + " (targetType was " + targetType.getName() + ")");
			return null;
		}

		if (null == value)
			return new Double(0);

		return value.doubleValue() * 100;
	}

	@Override
	public Class<BigDecimal> getModelType() {

		return BigDecimal.class;
	}

	@Override
	public Class<Double> getPresentationType() {
		return Double.class;
	}

}
