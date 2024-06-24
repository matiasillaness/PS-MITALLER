export interface ServiceResponseDTO {
    idServicio: number;
    nombre: string;
    descripcion: string;
    precio: number;
    tipo: string;
    activo: boolean;
}



export interface ServiceRequestDTO {
    nombre: string;
    descripcion: string;
    precio: number;
    tipo: string;
}
