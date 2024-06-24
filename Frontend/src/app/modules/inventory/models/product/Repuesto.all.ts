export interface RepuestoResponseDTO {
    disabled: boolean;
    idRepuesto: number;
    nombre: string;
    descripcion: string;
    precio: number;
    marca: string;
    activo: boolean;
    stock: number;

}

export interface RepuestoRequestDTO {
    nombre: string;
    descripcion: string;
    precio: number;
    idMarca: number | null;
    stock: number;
}

export interface RepuestoEditRequestDTO {
    idRepuesto: number;
    nombre: string;
    descripcion: string;
    precio: number;
    idMarca: number;
    stock: number;
    
}
