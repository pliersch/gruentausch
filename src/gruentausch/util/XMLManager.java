package gruentausch.util;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
		File file;
		try {
			file = FileAndFolderManager.createFile(path);
			Object result = JAXB.unmarshal(file, classesToBeBound);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
