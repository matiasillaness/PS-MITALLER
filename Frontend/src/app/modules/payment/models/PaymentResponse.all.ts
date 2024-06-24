

export interface VehiculoVentaResponse {
    patente: string;
    modelo: string;
    observaciones: string;
    idVehiculo: number;
    color: string;
    marca: string;
    activo: boolean;
    kilometraje: number;
    tipo_vehiculo: string;
    numero_chasis: string;
}

export interface DetalleVentaResponse {
    cantidad: number;
    precioUnitario: number;
    subtotal: number;
    descripcion: string;
    nombreRepuesto: string;
    nombreServicio: string;
}

export interface FacturaVentaResponse {
    fecha: string;
    tipoPago: string;
    tipoFactura: string;
    total: number;
    numeroFactura: string;
    descripcion: string;
    estado: string;
    dadaDeBaja: boolean;
    razonSocial: string;
    dniCliente: string;
    direccionCliente: string;
    telefonoCliente: string;
    nombreEmpleado: string;
    descuento: number;
    iva: number;
    vehiculo: VehiculoVentaResponse;
    detalles: DetalleVentaResponse[];
}