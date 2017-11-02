package gruentausch.util;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.ValidationEventCollector;

public class XMLManager {
	public boolean writeConsole(Object jaxbElement, Class<?>... classesToBeBound) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(classesToBeBound);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(jaxbElement, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public File writeFile(Object jaxbElement, String path) {
		File file = null;
		try {
			file = FileAndFolderManager.createFile(path);
			JAXB.marshal(jaxbElement, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	public Object readFile(String path, Class<?> classesToBeBound) {
		JAXBContext context = null;
		Unmarshaller unmarshaller = null;
		try {
			context = JAXBContext.newInstance( classesToBeBound );
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file;
		final ValidationEventCollector vec = new ValidationEventCollector();
		try {
			try {
				unmarshaller.setEventHandler(vec);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file = FileAndFolderManager.createFile(path);
			try {
				Object unmarshal = unmarshaller.unmarshal(file);
				return unmarshal;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
