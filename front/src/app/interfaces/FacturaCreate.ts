import {Item} from "./FacturaPage";

export interface FacturaCreate {
  nombre: string
  email: string
  userId: string
  items: Item[]
  descuento: number
  total: number
  totalIva: number
}
