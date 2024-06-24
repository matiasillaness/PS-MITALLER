export interface UserRequestDTO{
    email: string;
    nombre: string;
    apellido: string;
    telefono: string;
    direccion: string;
    role: string;

}

export interface UserRequestEmployeeDTO{
    email: string;
    nombre: string;
    apellido: string;
    telefono: string;
    direccion: string;
    password: string;
}