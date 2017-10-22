package gruentausch.parts;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import gruentausch.model.Day;
import gruentausch.model.Month;
import gruentausch.util.CalendarUtil;

public class MonthChart2 {

	JFreeChart chart;
	ChartPanel chartPanel;
	String chartTitle = "Programming Languages Trends";
	String categoryAxisLabel = "Interest over time";
	String valueAxisLabel = "Popularity";
	private DefaultCategoryDataset dataset;

	@Inject
	void updateDescription(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) Month month) {
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

		createDataset();

		chart = ChartFactory.createLineChart3D(chartTitle, categoryAxisLabel, valueAxisLabel, dataset);
		chartPanel = new ChartPanel(chart);

		parent.setLayout(new FormLayout());
		// default size
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		ChartComposite chartComposite = new ChartComposite(parent, SWT.NONE, chart, true);		
		FormData fd_chartComposite = new FormData();
		fd_chartComposite.top = new FormAttachment(0);
		fd_chartComposite.left = new FormAttachment(0);
		fd_chartComposite.bottom = new FormAttachment(0, 600);
		fd_chartComposite.right = new FormAttachment(0, 800);
		chartComposite.setLayoutData(fd_chartComposite);
		chartComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
	}

	private CategoryDataset createDataset() {
		dataset = new DefaultCategoryDataset();
		String series1 = "Paul";
		String series2 = "default";

		dataset.addValue(5.0, series1, "2005");
		dataset.addValue(4.8, series1, "2006");
		dataset.addValue(4.5, series1, "2007");
		dataset.addValue(4.3, series1, "2008");
		dataset.addValue(4.0, series1, "2009");
		dataset.addValue(4.1, series1, "2010");
		dataset.addValue(4.2, series1, "2011");
		dataset.addValue(4.2, series1, "2012");
		dataset.addValue(4.0, series1, "2013");

		dataset.addValue(4.0, series2, "2005");
		dataset.addValue(4.2, series2, "2006");
		dataset.addValue(3.8, series2, "2007");
		dataset.addValue(3.6, series2, "2008");
		dataset.addValue(3.4, series2, "2009");
		dataset.addValue(3.4, series2, "2010");
		dataset.addValue(3.3, series2, "2011");
		dataset.addValue(3.1, series2, "2012");
		dataset.addValue(3.2, series2, "2013");
		
		return dataset;
	}

	private CategoryDataset createDataset(Month month) {
		String series1 = "Paul";
		String series2 = "default";

		List<Day> days = month.getDays();

		int date = 1;
		for (Day day : days) {
			Calendar workingTime = CalendarUtil.getWorkingTime(day.getBegin(), day.getEnd());
			int hour = workingTime.get(Calendar.HOUR_OF_DAY);
			float min = workingTime.get(Calendar.MINUTE) / 60f;
			dataset.addValue(hour + min, series1, date++ + "");
			dataset.addValue(8, series2, date + "");
		}

		return dataset;
	}
}
