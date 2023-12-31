package ec.gob.sri.comprobantes.xml;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import ec.gob.sri.comprobantes.modelo.ComprobanteXml;

public class ComprobanteXmlConverter implements Converter {
    public ComprobanteXmlConverter() {
    }

    public boolean canConvert(Class clazz) {
        return clazz.equals(ComprobanteXml.class);
    }

    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext mc) {
        ComprobanteXml i = (ComprobanteXml)o;
        writer.setValue(i.getFileXML());
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc) {
        ComprobanteXml item = new ComprobanteXml();
        item.setTipo(reader.getAttribute("tipo"));
        item.setVersion(reader.getAttribute("version"));
        item.setFileXML(reader.getValue());
        return item;
    }
}
