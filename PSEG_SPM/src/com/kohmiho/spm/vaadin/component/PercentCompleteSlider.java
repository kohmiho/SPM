package com.kohmiho.spm.vaadin.component;

import java.text.NumberFormat;

import com.kohmiho.spm.vaadin.converter.PercentToBigDecimalConverter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PercentCompleteSlider extends CustomComponent {

	private static final NumberFormat FORMATTER_PERCENT = NumberFormat.getPercentInstance();

	private Label label = new Label();
	private Slider slider = new Slider();

	public PercentCompleteSlider() {
		this("100px", "30px");
	}

	public PercentCompleteSlider(String sliderWidth, String labelWidth) {

		VerticalLayout content = new VerticalLayout();

		// Compose from multiple components
		content.addComponent(label);
		content.addComponent(slider);

		slider.setMin(0);
		slider.setMax(100);
		slider.setResolution(0);
		slider.setConverter(new PercentToBigDecimalConverter());

		slider.setWidth(sliderWidth);
		slider.setHeight("-1px");

		label.setWidth(labelWidth);
		label.setHeight("-1px");
		label.setValue("0%");

		// // Set the size as undefined at all levels
		// setSizeUndefined();

		slider.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				double value = (Double) event.getProperty().getValue();
				label.setValue(FORMATTER_PERCENT.format(value / 100));
			}
		});

		// The composition root MUST be set
		setCompositionRoot(content);
	}

	public Slider getSlider() {
		return slider;
	}
}
