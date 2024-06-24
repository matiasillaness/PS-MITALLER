import { UserDTO } from "./UserDTO";

export interface AuthResponseDTO {
    token: string;
    type: string;
    expiresIn: string;
    message: string;
    userRegister: UserDTO;
    status: number;
}