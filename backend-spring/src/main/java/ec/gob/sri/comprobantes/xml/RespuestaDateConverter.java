package ec.gob.sri.comprobantes.xml;

//import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import ec.gob.sri.comprobantes.util.Constantes;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

// XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar();
public class RespuestaDateConverter implements Converter {
    public RespuestaDateConverter() {
    }

    public boolean canConvert(Class clazz) {
        return clazz.equals(XMLGregorianCalendar.class);
    }

    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
        XMLGregorianCalendar i = (XMLGregorianCalendar)o;
        writer.setValue(Constantes.dateTimeFormat.format(i.toGregorianCalendar().getTime()));
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        Date date = null;

        try {
            date = Constantes.dateTimeFormat.parse(reader.getValue());
        } catch (ParseException var6) {
            Logger.getLogger(RespuestaDateConverter.class.getName()).log(Level.SEVERE, (String)null, var6);
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        //XMLGregorianCalendarImpl item = new XMLGregorianCalendarImpl(cal);
        XMLGregorianCalendar item;
        try {
            item = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        return item;
    }
}
