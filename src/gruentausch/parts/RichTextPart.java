package gruentausch.parts;

import javax.annotation.PostConstruct;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.paperclips.core.Print;
import org.eclipse.nebula.paperclips.core.text.StyledTextPrint;
import org.eclipse.nebula.paperclips.core.text.TextStyle;
import org.eclipse.nebula.widgets.richtext.RichTextEditor;
import org.eclipse.nebula.widgets.richtext.RichTextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RichTextPart {

	@PostConstruct
	public void createControls(Composite parent) {
				
		parent.setLayout(new GridLayout(2, true));

		final RichTextEditor editor = new RichTextEditor(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(editor);

		editor.setText(getHtmlText());

		final RichTextViewer viewer = new RichTextViewer(parent, SWT.BORDER | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).span(1, 2).applyTo(viewer);

		viewer.setText(getHtmlText());

		final Text htmlOutput = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(SWT.DEFAULT, 100).applyTo(htmlOutput);

		Composite buttonPanel = new Composite(parent, SWT.NONE);
		buttonPanel.setLayout(new RowLayout());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(buttonPanel);

		Button getButton = new Button(buttonPanel, SWT.PUSH);
		getButton.setText("Get text");
		getButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
		
//				boolean executeJavascript = editor.executeJavascript("javascript:document.title = \"Print page title\";");
				editor.executeJavascript("javascript:window.print();");

				// String htmlText = editor.getText();
				// viewer.setText(htmlText);
				htmlOutput.setText(getHtmlText());

				// Show the print dialog
				Display display = Display.getDefault();
				Shell shell = new Shell(display);
				PrintDialog dialog = new PrintDialog(shell, SWT.NONE);
				PrinterData printerData = dialog.open();

//				// Print the document to the printer the user selected.
//				if (printerData != null) {
//					PrintJob job = new PrintJob("TutorialExample1.java", createPrint());
//					job.setMargins(72);
//					PaperClips.print(job, printerData);
//				}
			}
		});
	}

	private Print createPrint() {
		StyledTextPrint doc = new StyledTextPrint();
		TextStyle normal = new TextStyle().font("Arial", 14, SWT.NORMAL);
		TextStyle bold = normal.fontStyle(SWT.BOLD);
		TextStyle big = normal.fontHeight(20);
		TextStyle italic = normal.fontStyle(SWT.ITALIC);
		TextStyle monospace = normal.fontName("Courier");
		TextStyle underline = normal.underline();
		TextStyle strikeout = normal.strikeout();

		doc.setStyle(normal).append("This snippet demonstrates the use of ").append("StyledTextPrint", monospace)
				.append(" for creating bodies of styled text.").newline().newline().append("StyledTextPrint", monospace)
				.append(" makes sure that ").append("text ", bold).append("of ", italic)
				.append("different ", normal.fontHeight(20)).append("font ", normal.fontHeight(42))
				.append("names,", normal.fontName("Courier")).append(" sizes, ", normal.fontHeight(10)).append("and ")
				.append("styles", normal.underline()).append(" are aligned correctly along the base line.").newline().newline()
				.append("With ").append("StyledTextPrint", monospace)
				.append(" you can embed any other printable element alongside the text.  ")
				.append("For example, here is an image ").append(" and a horizontal line").newline().setStyle(italic)
				.append("Note that some elements like GridPrint tend to be broken unnaturally across lines, and "
						+ "therefore may not be suitable for use in a StyledTextPrint.")
				.setStyle(normal).newline().newline().append("Many text styles are possible such as ")
				.append("bold print", bold).append(", ").append("italic print", italic).append(", ")
				.append("strikeout text", strikeout).append(", ").append("underlined text", underline).append(", or ")
				.append("any combination of the above", normal.fontStyle(SWT.BOLD | SWT.ITALIC).strikeout().underline())
				.append(".").newline().newline().append("You can also set ")
				.append("foreground colors", normal.foreground(0x00A000)).append(" or ")
				.append("background colors", normal.background(0xFFFFA0)).append(" on the text through the TextStyle class.")
				.newline().newline().append("Enjoy!", big);
		return doc;

	}

	private String getHtmlText() {
		return "<p><strong>Patrick Liersch</strong><br />\r\n" + "<em>Am Hang 301</em><br />\r\n"
				+ "<span style=\"color:rgb(0, 0, 255)\"><span style=\"font-family:courier new,courier,monospace\">159113 Goyatz</span></span></p>";
	}

}
