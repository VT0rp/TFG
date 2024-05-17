export interface FacturaPage {
  content: Factura[]
  pageable: Pageable
  last: boolean
  totalPages: number
  totalElements: number
  size: number
  number: number
  sort: Sort2
  numberOfElements: number
  first: boolean
  empty: boolean
}

export interface Factura {
  id: string
  nombre: string
  email: string
  userId: string
  items: Item[]
  descuento: number
  total: number
  totalIva: number
}

export interface Item {
  nombre: string
  cantidad: number
  precio: number
  iva: number
  descuento: number
}

export interface Pageable {
  pageNumber: number
  pageSize: number
  sort: Sort
  offset: number
  unpaged: boolean
  paged: boolean
}

export interface Sort {
  empty: boolean
  sorted: boolean
  unsorted: boolean
}

export interface Sort2 {
  empty: boolean
  sorted: boolean
  unsorted: boolean
}
