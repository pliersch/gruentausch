package gruentausch.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import gruentausch.util.FileAndFolderManager;

public class PdfCreator {

	public void print() {
		// createEmptyDocument();
		createHelloDocument();
	}

	private void createEmptyDocument() {
		try (final PDDocument document = new PDDocument()) {
			final PDPage emptyPage = new PDPage();
			document.addPage(emptyPage);
			File file = FileAndFolderManager.createFile("data/EmptyPage.pdf");
			String absolutePath = file.getAbsolutePath();

			document.save(absolutePath);
		} catch (IOException ioEx) {
			System.err.println("Exception while trying to create blank document - " + ioEx);
		}
	}

	private void createHelloDocument() {

		
		final PDPage singlePage = new PDPage();
		final PDFont courierBoldFont = PDType1Font.COURIER_BOLD;
		final int fontSize = 12;
		try (final PDDocument document = new PDDocument()) {
			File file = FileAndFolderManager.createFile("data/EmptyPage.pdf");
			String absolutePath = file.getAbsolutePath();
			
			document.addPage(singlePage);
			final PDPageContentStream contentStream = new PDPageContentStream(document, singlePage);
			contentStream.beginText();
			contentStream.setFont(courierBoldFont, fontSize);
			contentStream.newLineAtOffset(150, 750);
			contentStream.showText("Hello PDFBox");
			contentStream.endText();
			contentStream.close(); // Stream must be closed before saving document.
			document.save(absolutePath);
		} catch (IOException ioEx) {
			System.err.println("Exception while trying to create blank document - " + ioEx);
		}
	}

}
