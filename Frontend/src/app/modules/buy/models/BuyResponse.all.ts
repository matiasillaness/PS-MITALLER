export interface BuyResponse{
    nombreCompra: string;
    fecha: string;
    tipoPago: string;
    total: number;
    descripcion: string;
    proveedor: ProviderBuyResponse;
    detalles: DetailsBuyResponse[];
}

export interface DetailsBuyResponse{
    cantidad: number;
    precioUnitario: number;
    subtotal: number;
    nombreRepuesto: string;
}

export interface ProviderBuyResponse{
    idProveedor: number;
    nombre: string;
    telefono: string;
    direccion: string;
    email: string;
    descripcion: string;
    tipoProveedor: string;
}

