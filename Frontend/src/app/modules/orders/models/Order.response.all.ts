


export interface OrderDisponibleResponse {
    fecha: string;
    total: number;
    id: number;
}


export interface OrderResponse {
    fecha: string;
    id: number;
    mercadoPagoId: string;
    tipoOrden: string;
    estado: string;
    estadoMercadoPago: string;
    total: number;
    patenteDelVehiculo: string;
    modeloDelVehiculo: string;
    recordatorioEnviado: boolean;
}

