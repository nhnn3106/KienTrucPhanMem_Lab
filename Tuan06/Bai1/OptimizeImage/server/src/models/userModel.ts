import db from '../db.js'

type UserRow = {
    id: number
    name: string
    email: string
    role: string
    status: 'active' | 'inactive'
    created_at: string
}

export type User = {
    id: number
    name: string
    email: string
    role: string
    status: 'active' | 'inactive'
    createdAt: string
}

export type NewUser = {
    name: string
    email: string
    role: string
    status: 'active' | 'inactive'
}

const mapUser = (row: UserRow): User => ({
    id: row.id,
    name: row.name,
    email: row.email,
    role: row.role,
    status: row.status,
    createdAt: row.created_at,
})

export const listUsers = async (): Promise<User[]> => {
    const { rows } = await db.query<UserRow>(
        'SELECT id, name, email, role, status, created_at FROM users ORDER BY id DESC',
    )
    return rows.map(mapUser)
}

export const createUser = async (payload: NewUser): Promise<User> => {
    const { rows } = await db.query<UserRow>(
        `INSERT INTO users (name, email, role, status)
     VALUES ($1, $2, $3, $4)
     RETURNING id, name, email, role, status, created_at`,
        [payload.name, payload.email, payload.role, payload.status],
    )
    return mapUser(rows[0])
}

export const deleteUser = async (id: number): Promise<User | null> => {
    const { rows } = await db.query<UserRow>(
        'DELETE FROM users WHERE id = $1 RETURNING id, name, email, role, status, created_at',
        [id],
    )
    return rows[0] ? mapUser(rows[0]) : null
}
