export interface BuyRequest {
    descripcion: string;
    iva: number;
    idProveedor: number;
    tipoPago: string;
    detalleCompraRequest: DetailsBuyRequest[];
}


export interface DetailsBuyRequest {
    idRepuesto: number;
    cantidad: number;
    precioUnitario: number;
}