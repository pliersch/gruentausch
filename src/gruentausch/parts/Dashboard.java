package gruentausch.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.jfree.util.Rotation;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class Dashboard {

	 @PostConstruct
	 public void createControls(Composite parent) {
	        PieDataset dataset = createDataset();
	        // based on the dataset we create the chart
	        JFreeChart chart = createChart(dataset, "chartTitle");
	        // we put the chart into a panel
	        ChartPanel chartPanel = new ChartPanel(chart);
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

	private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;

    }

    /**
     * Creates a chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

}
