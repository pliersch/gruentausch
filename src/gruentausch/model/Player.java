package gruentausch.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Player
{
  private String name;
  private Date   birthday;

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public void setBirthday( Date birthday )
  {
    this.birthday = birthday;
  }

  public Date getBirthday()
  {
    return birthday;
  }
}
