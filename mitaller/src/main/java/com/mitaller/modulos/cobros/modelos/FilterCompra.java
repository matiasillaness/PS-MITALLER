package com.mitaller.modulos.cobros.modelos;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FilterCompra {
    private String fechaInicio;
    private String fechaFin;
    private BigDecimal totalMayorA;
    private BigDecimal totalMenorA;
    private String nombreCompra;
    private String proveedor;
    private String tipoPago;
    private Boolean dadaDeBaja;
    private String numeroDeTelefono;
    private String email;

    public void limpiarEspaciosBlancos(){
        if(this.fechaInicio != null){
            this.fechaInicio = this.fechaInicio.trim();
        }
        if(this.fechaFin != null){
            this.fechaFin = this.fechaFin.trim();
        }
        if(this.nombreCompra != null){
            this.nombreCompra = this.nombreCompra.trim();
        }
        if(this.proveedor != null){
            this.proveedor = this.proveedor.trim();
        }
        if(this.tipoPago != null){
            this.tipoPago = this.tipoPago.trim();
        }
        if(this.numeroDeTelefono != null){
            this.numeroDeTelefono = this.numeroDeTelefono.trim();
        }
        if(this.email != null){
            this.email = this.email.trim();
        }
    }
}
