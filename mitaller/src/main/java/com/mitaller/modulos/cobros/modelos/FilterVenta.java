package com.mitaller.modulos.cobros.modelos;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FilterVenta {
    private String fechaInicio;
    private String fechaFin;
    private String tipoPago;
    private String tipoFactura;
    private String numeroFactura;
    private String descripcion;
    private String cliente;
    private Boolean dadaDeBaja;

    public void trimAll(){
        if(this.fechaInicio != null){
            this.fechaInicio = this.fechaInicio.trim();
        }
        if(this.fechaFin != null){
            this.fechaFin = this.fechaFin.trim();
        }
        if(this.tipoPago != null){
            this.tipoPago = this.tipoPago.trim();
        }
        if(this.tipoFactura != null){
            this.tipoFactura = this.tipoFactura.trim();
        }
        if(this.numeroFactura != null){
            this.numeroFactura = this.numeroFactura.trim();
        }
        if(this.descripcion != null){
            this.descripcion = this.descripcion.trim();
        }
        if(this.cliente != null){
            this.cliente = this.cliente.trim();
        }
    }
}
