package gruentausch.parts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.ui.Layer;

import gruentausch.model.Day;
import gruentausch.model.Month;
import gruentausch.util.CalendarUtil;

public class MonthChart {

	JFreeChart chart;
	ChartPanel chartPanel;
	String chartTitle = "das ist ein Titel";
	String categoryAxisLabel = "Tage";
	String valueAxisLabel = "Stunden";
	private DefaultCategoryDataset dataset;

	@Inject
	void updateMonth(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Month month) {
		if (month != null) {
			updateChart(month);
		}
	}

	private void updateChart(Month month) {
		dataset.clear();
		createDataset(month);
		chartPanel.repaint();
		chartPanel.updateUI();
	}

	@PostConstruct
	public void createControls(Composite parent) {
		dataset = new DefaultCategoryDataset();

		chart = ChartFactory.createBarChart(chartTitle, categoryAxisLabel, valueAxisLabel, dataset);
		chart.removeLegend();
		chartPanel = new ChartPanel(chart);

		CategoryPlot plot = chart.getCategoryPlot();
		ValueMarker mark = new ValueMarker(8, Color.BLUE, new BasicStroke(3.0F));
		plot.addRangeMarker(mark, Layer.BACKGROUND);

		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		new ChartComposite(parent, SWT.NONE, chart, true);
	}

	private CategoryDataset createDataset(Month month) {
		String series1 = "Paul";

		List<Day> days = month.getDays();
		int hour;
		float min;
		int date = 1;
		for (Day day : days) {
			if (day.getBegin() != null && day.getEnd() != null) {
				Calendar workingTime = CalendarUtil.getWorkingTime(day.getBegin(), day.getEnd());
				hour = workingTime.get(Calendar.HOUR_OF_DAY);
				min = workingTime.get(Calendar.MINUTE) / 60f;
			} else {
				hour = 0;
				min = 0;
			}
			dataset.addValue(hour + min, series1, date++ + "");
		}
		return dataset;
	}
}
