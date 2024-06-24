export interface DetalleVentaRequest {
    cantidad: number;
    idRepuesto: number | null;
    idServicio: number | null;
  }

  export interface VentaRequest {
    tipoPago: string;
    tipoFactura: string;
    razonSocial: string;
    dniCliente: string;
    direccionCliente: string;
    descuento: number;
    telefonoCliente: string;
    email: string;
    idVehiculo: number;
    detalleVentaRequest: DetalleVentaRequest[];
    descripcion: string;
    mercadoPago: boolean;
  }  


