export interface OrderPartialRequest {
    fecha: string;
    total: number;  
} 

export interface OrderForClientRequest {
    patenteDelVehiculo: string;
    modeloDelVehiculo: string;
    tipoOrden: string;
    emailDelCliente: string;
}

