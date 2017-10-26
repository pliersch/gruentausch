package gruentausch.util.jaxb;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date>
{
  private final static DateFormat formatter = new SimpleDateFormat( 
    "dd/MM/yyyy" );

  public Date unmarshal( String date ) throws ParseException {
    return formatter.parse( date );
  }


  public String marshal( Date date )  {
    return formatter.format( date );
  }
}