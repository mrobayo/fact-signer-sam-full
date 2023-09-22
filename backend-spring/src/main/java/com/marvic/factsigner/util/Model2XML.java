package com.marvic.factsigner.util;

import com.marvic.factsigner.model.comprobantes.FacturaComp;
import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.model.sistema.Empresa;
import ec.gob.sri.comprobantes.modelo.InfoTributaria;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.modelo.factura.Impuesto;
import ec.gob.sri.types.SriImpuesto;

import static com.marvic.factsigner.util.Utils.*;

public class Model2XML {

    public static final String ID = "comprobante";
    public static final String VERSION = "1.1.0";


    public static Factura generarComprobante(FacturaComp entity) {
        Empresa empresa = entity.getEmpresa();

        Factura xml = new Factura();
        xml.setId(ID);
        xml.setVersion(VERSION);
        xml.setInfoTributaria(new InfoTributaria());
        xml.setInfoFactura(new Factura.InfoFactura());
        xml.setDetalles(new Factura.Detalles());
        xml.setInfoAdicional(new Factura.InfoAdicional());

        InfoTributaria i = xml.getInfoTributaria();
        i.setTipoEmision("1");
        i.setAmbiente(entity.getAmbiente());

        i.setRazonSocial(removeAccents(empresa.getName()));
        i.setNombreComercial(removeAccents(empresa.getComercial()));
        i.setRuc(empresa.getRuc());
        i.setDirMatriz(removeAccents(empresa.getDireccion()));

        i.setClaveAcceso(entity.getClaveAcceso());
        i.setCodDoc(entity.getCodDoc());
        i.setEstab(entity.getEstab());
        i.setPtoEmi(entity.getPtoEmi());
        i.setSecuencial(entity.getSecuencial());


        Factura.InfoFactura f = xml.getInfoFactura();
        if (entity.getFechaEmision() != null) {
            f.setFechaEmision(fmtDMY(entity.getFechaEmision()));
        }
        if (empresa.getNumeroContribuyente() != null) {
            f.setContribuyenteEspecial(empresa.getNumeroContribuyente());
        }
        f.setDirEstablecimiento(empresa.getDireccion());
        f.setObligadoContabilidad(empresa.isObligado()?"SI":"NO");

        // Cliente cliente = entity.getCliente();
        //f.setRazonSocialComprador(removeAccents(cliente.getName()));
        //f.setTipoIdentificacionComprador(cliente.getTipo().value());
        //f.setIdentificacionComprador(cliente.getIdentidad());
        //f.setDireccionComprador(removeAccents(cliente.getDireccion()));

        f.setRazonSocialComprador(removeAccents(entity.getRazonSocialComprador()));
        f.setTipoIdentificacionComprador(entity.getTipoIdentificacionComprador().value());
        f.setIdentificacionComprador(entity.getIdentificacionComprador());
        f.setDireccionComprador(removeAccents(entity.getDireccionComprador()));

        f.setMoneda(empresa.getMoneda());

        if (entity.getPagos() != null) {
            f.setPagos(new Factura.InfoFactura.Pago());
            entity.getPagos().forEach((formaPago) -> {
                Factura.InfoFactura.Pago.DetallePago pago = new Factura.InfoFactura.Pago.DetallePago();
                f.getPagos().getPagos().add(pago);

                pago.setFormaPago(formaPago.getFormaPago());
                pago.setPlazo(""+formaPago.getPlazo());
                pago.setTotal(formaPago.getTotal());
                pago.setUnidadTiempo("dias");
            });
        }
        f.setPropina(to2Dec( entity.getPropina() ));
        f.setTotalSinImpuestos( entity.getTotalSinImpuestos() );
        f.setTotalDescuento( entity.getTotalDescuento() );
        //TODO f.setTotalConImpuestos( impuestosSri(factory) );
        f.setImporteTotal( entity.getImporteTotal() );

        if (entity.getInfoAdicional() != null) {
            entity.getInfoAdicional().forEach((key, value) -> {
                Factura.InfoAdicional.CampoAdicional c = new Factura.InfoAdicional.CampoAdicional();
                c.setNombre(key);
                c.setValue(value);
                xml.getInfoAdicional().getCampoAdicional().add(c);
            });
        }

        if (entity.getDetalles() != null) {
            entity.getDetalles().forEach((value) -> {
                Factura.Detalles.Detalle detalle = new Factura.Detalles.Detalle();

                detalle.setCodigoPrincipal(value.getCodigoPrincipal());
                detalle.setDescripcion(removeAccents(value.getDescripcion()));

                detalle.setCantidad(value.getCantidad());
                detalle.setPrecioUnitario(value.getPrecioUnitario());
                detalle.setDescuento(value.getDescuento());
                detalle.setPrecioTotalSinImpuesto(value.getPrecioTotalSinImpuesto());

                if (value.getIva() != null) {
                    detalle.setImpuestos(new Factura.Detalles.Detalle.Impuestos());
                    Impuesto iva = new Impuesto();
                    detalle.getImpuestos().getImpuesto().add(iva);

                    iva.setCodigo(SriImpuesto.IVA.value());
                    iva.setCodigoPorcentaje(""+value.getIva().getCodigoPorcentaje());
                    iva.setTarifa(value.getIva().getTarifa());
                    iva.setBaseImponible(value.getIva().getBaseImponible());
                    iva.setValor(value.getIva().getValor());
                }

                xml.getDetalles().getDetalle().add(detalle);
            });
        }

        return xml;
    }

}
