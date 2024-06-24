export interface UserResponseDTO {
    email: string;
    nombre: string;
    apellido: string;
    telefono: string;
    role: 'ROLE_USER' | 'ROLE_ADMIN' | 'ROLE_EMPLOYEE'; // Dependiendo de los roles disponibles
    direccion: string;
    activo: boolean;
}